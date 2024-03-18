package com.br.bruno.appweb.service;

import com.br.bruno.appweb.events.EventBus;
import com.br.bruno.appweb.events.LandmarkDetectionEventListener;
import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.vision.FaceDetectionMessage;
import com.br.bruno.appweb.models.vision.landmarks.LandmarkDetectionMessage;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

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
        this.eventBus.subscribe(LandmarkDetectionMessage.class, new LandmarkDetectionEventListener(this));
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
    public void storeLandmarkImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + file.getOriginalFilename();
            BlobId blobId = BlobId.of(landmarkBucketName, fileName); // Use o nome do bucket para landmarks
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(FaceDetectionMessage event) {
        System.out.println("Mensagem recebida pelo UploadServiceVision: " + event.getFaceData());
        simpMessagingTemplate.convertAndSend("/topic/analysisResult", event.getFaceData());
        System.out.println("Enviado...");
    }

    //Quando uma mensagem é publicada no topico (Pub/Sub) do GCloud, essa mensagem terá o formato do Objeto LandmarkDetectionMessage
    public void onEvent(LandmarkDetectionMessage event) {
        System.out.println("Mensagem recebida pelo UploadServiceLandmarks: " + event.getLandmarkData());
        simpMessagingTemplate.convertAndSend("/topic/analysisResultLandmarks", event.getLandmarkData());
        System.out.println("Enviado...");
    }
}
