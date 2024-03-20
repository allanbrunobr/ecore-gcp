package com.br.bruno.appweb.service;

import com.br.bruno.appweb.events.EventBus;
import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.vision.FaceDetectionMessage;
import com.br.bruno.appweb.models.vision.landmarks.LandmarkData;
import com.br.bruno.appweb.models.vision.landmarks.LandmarkDetectionMessage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    public VisionService(EventBus eventBus, SimpMessagingTemplate simpMessagingTemplate) {
        this.eventBus = eventBus;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.eventBus.subscribe(FaceDetectionMessage.class, this);
        this.storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    }

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
    public CompletableFuture<List<AnnotateImageResponse>> detectLandmarkImage(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", ".jpg");
        Files.write(tempFile.toPath(), file.getBytes());
        String filePath = tempFile.getAbsolutePath();
        List<AnnotateImageResponse> listReturn = detectLandmarks(filePath);
        tempFile.delete();
        return CompletableFuture.completedFuture(listReturn);
    }

    @Override
    public void onEvent(FaceDetectionMessage event) {
        System.out.println("Mensagem recebida pelo UploadServiceVision: " + event.getFaceData());
        simpMessagingTemplate.convertAndSend("/topic/analysisResult", event.getFaceData());
        System.out.println("Enviado...");
    }

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

    public void sendLandmarkData(CompletableFuture<List<AnnotateImageResponse>> landmarkDataListFuture) {
        landmarkDataListFuture.thenAccept(landmarkDataList -> {
            simpMessagingTemplate.convertAndSend("/topic/landmarkData", landmarkDataList);
        });
    }

}
