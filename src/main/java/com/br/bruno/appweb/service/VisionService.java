package com.br.bruno.appweb.service;

import com.br.bruno.appweb.events.EventBus;
import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.vision.FaceDetectionMessage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * The VisionService class provides functionality for storing images in a
 * Google Cloud Storage bucket and detecting landmarks in an image using
 * the Google Cloud Vision API.
 */
@Service
public class VisionService implements EventListener<FaceDetectionMessage> {
  @Value("${project.id}")
  private String projectId;
  @Value("${bucket.name}")
  private String bucketName;
  @Value("${bucket.name.landmarks}")
  private String landmarkBucketName;
  private EventBus eventBus;
  private Storage storage;
  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * Constructs a new VisionService with the specified EventBus and SimpMessagingTemplate.
   *
   * @param eventBus              The EventBus to be used by the service.
   * @param simpMessagingTemplate The SimpMessagingTemplate to be used by the service.
   */
  public VisionService(EventBus eventBus, SimpMessagingTemplate simpMessagingTemplate) {
    this.eventBus = eventBus;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.eventBus.subscribe(FaceDetectionMessage.class, this);
    this.storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
  }
  /**
   * Stores the given file in the bucket.
   *
   * @param file the file to store
   */

  public void store(MultipartFile file) {
    try {
      String fileName = UUID.randomUUID() + file.getOriginalFilename();
      BlobId blobId = BlobId.of(bucketName, fileName);
      BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
      storage.create(blobInfo, file.getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Detects landmarks in an image.
   *
   * @param file the image file
   * @return a CompletableFuture with a List of AnnotateImageResponse objects
   * @throws IOException if there is an error reading the file
   */
  public CompletableFuture<List<AnnotateImageResponse>> detectLandmarkImage(
            MultipartFile file) throws IOException {
    File tempFile = File.createTempFile("temp", ".jpg");
    Files.write(tempFile.toPath(), file.getBytes());
    String filePath = tempFile.getAbsolutePath();
    List<AnnotateImageResponse> listReturn = detectLandmarks(filePath);
    tempFile.delete();
    return CompletableFuture.completedFuture(listReturn);
  }

  /**
   * Handles the FaceDetectionMessage event.
   *
   * @param event the FaceDetectionMessage event
   */
  @Override
  public void onEvent(FaceDetectionMessage event) {
    System.out.println("Mensagem recebida pelo UploadServiceVision: " + event.getFaceData());
    simpMessagingTemplate.convertAndSend("/topic/analysisResult", event.getFaceData());
    System.out.println("Enviado...");
  }

  /**
   * Detects landmarks in an image.
   *
   * @param filePath The file path of the image
   * @return A list of AnnotateImageResponse objects
   * @throws IOException If there is an error reading the file
   */
  public List<AnnotateImageResponse> detectLandmarks(String filePath) throws IOException {
    List<AnnotateImageRequest> requests = new ArrayList<>();
    ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));
    Image img = Image.newBuilder().setContent(imgBytes).build();
    Feature feat = Feature.newBuilder().setType(Feature.Type.LANDMARK_DETECTION).build();
    AnnotateImageRequest request =
          AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
    requests.add(request);
    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();
      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.format("Error: %s%n", res.getError().getMessage());
          return new ArrayList<>();
        }
      }
      return responses;
    }
  }
}
