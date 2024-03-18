package com.br.bruno.appweb.events;

import com.br.bruno.appweb.interfaces.EventListener;
import com.br.bruno.appweb.models.vision.landmarks.LandmarkDetectionMessage;
import com.br.bruno.appweb.service.VisionService;

public class LandmarkDetectionEventListener implements EventListener<LandmarkDetectionMessage> {
    private final VisionService visionService;

    public LandmarkDetectionEventListener(VisionService visionService) {
        this.visionService = visionService;
    }

    @Override
    public void onEvent(LandmarkDetectionMessage event) {
        visionService.onEvent(event);
    }
}

