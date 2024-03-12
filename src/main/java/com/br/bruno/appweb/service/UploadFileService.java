package com.br.bruno.appweb.service;

import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.cvision.FaceDetectionMessage;
import com.br.bruno.appweb.events.EventBus;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileService implements EventListener<FaceDetectionMessage> {
    String projectId = "app-springboot-project";
    String bucketName = "app-springboot-project-images";

    private EventBus eventBus;

    public UploadFileService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.subscribe(FaceDetectionMessage.class, this);
    }
    public void store(MultipartFile file) throws IOException {

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());

    }
    @Override
    public void onEvent(FaceDetectionMessage event) {
        // Lidar com o evento recebido
        System.out.println("Mensagem recebida pelo UploadService: " + event.toString());
    }
}