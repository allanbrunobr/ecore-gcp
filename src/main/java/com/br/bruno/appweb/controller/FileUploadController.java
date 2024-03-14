package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.UploadFileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class FileUploadController {

    private final UploadFileService uploadService;

    public FileUploadController(UploadFileService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/uploadFileToStorage")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String message;
        ModelAndView modelAndView = new ModelAndView("vision/gcloud-vision-result");
        try {
            uploadService.store(file);
            message = "Upload do arquivo realizado com sucesso! " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            message = "Falha ao fazer upload do arquivo " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("error", message);
        }
        return modelAndView;
    }


 }

