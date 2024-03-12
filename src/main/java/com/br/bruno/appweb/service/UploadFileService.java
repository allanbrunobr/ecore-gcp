package com.br.bruno.appweb.service;

import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.cvision.FaceDetectionMessage;
import com.br.bruno.appweb.events.EventBus;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileService implements EventListener<FaceDetectionMessage> {
    @Value("${project.id}")
    private String projectId;
    @Value("${bucket.name}")
    private String bucketName;
    private EventBus eventBus;
    private Storage storage;
    private final SimpMessagingTemplate simpMessagingTemplate;


    public UploadFileService(EventBus eventBus, SimpMessagingTemplate simpMessagingTemplate) {
        this.eventBus = eventBus;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.eventBus.subscribe(FaceDetectionMessage.class, this);
        this.storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

    }
    public void store(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onEvent(FaceDetectionMessage event) {
        System.out.println("Mensagem recebida pelo UploadService: " + event.toString());
        simpMessagingTemplate.convertAndSend("/topic/result", event.toString());
        System.out.println("Enviado...");


    }
}