package com.br.bruno.appweb.controller;

import com.br.bruno.appweb.service.VisionService;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class VisionController {

    private final VisionService visionService;

    public VisionController(VisionService visionService) {
        this.visionService = visionService;
    }

    @PostMapping("/uploadFileToVisionFace")
    public ModelAndView handleFileUploadVisionFace(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String message;
        ModelAndView modelAndView = new ModelAndView("ai/vision/vision-face-result");
        try {
            visionService.store(file);
            message = "Upload do arquivo realizado com sucesso! " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("message", message);
        } catch (Exception e) {
            message = "Falha ao fazer upload do arquivo " + file.getOriginalFilename() + "!";
            redirectAttributes.addFlashAttribute("error", message);
        }
        return modelAndView;
    }


    @PostMapping("/uploadFileToVisionLandmarks")
    public ModelAndView handleFileUploadVisionLandmarks(@RequestParam("file") MultipartFile file, Model model) {
        ModelAndView modelAndView = new ModelAndView("ai/vision/vision-landmark-result");
//        try {
//            CompletableFuture<List<AnnotateImageResponse>> annotateImageResponseList = visionService.detectLandmarkImage(file);
//            modelAndView.addObject("annotateImageResponseList", annotateImageResponseList);
//            visionService.sendLandmarkData(annotateImageResponseList);

            try {
                visionService.detectLandmarkImage(file).thenAccept(landmarkDataList -> {
                    model.addAttribute("landmarkDataList", landmarkDataList);

                });

        } catch (Exception e) {
        }
        return modelAndView;
    }

 }

