package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.UploadFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class FileUploadController {

    private final UploadFileService uploadService;

    public FileUploadController(UploadFileService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/uploadFileToStorage")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String message;
        try {
            uploadService.store(file);
            message = "Upload do arquivo realizado com sucesso! " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            message = "Falha ao fazer upload do arquivo " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("error", message);
        }
        return "redirect:/gcloud-vision.html"; // Redireciona para a página gcloud-vision.html
    }

 }

