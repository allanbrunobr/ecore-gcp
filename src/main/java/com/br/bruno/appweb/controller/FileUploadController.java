package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.UploadFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    private final UploadFileService uploadService;

    public FileUploadController(UploadFileService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/uploadFileToStorage")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            uploadService.store(file);
            message = "Upload do arquivo realizado com sucesso! " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        } catch (Exception e) {
            message = "Falha ao fazer upload do arquivo " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }
}
