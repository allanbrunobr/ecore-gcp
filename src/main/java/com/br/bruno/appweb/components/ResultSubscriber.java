package com.br.bruno.appweb.components;

import com.br.bruno.appweb.models.cvision.FaceDetectionMessage;
import com.br.bruno.appweb.events.EventBus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ResultSubscriber {

    private EventBus eventBus;

    public ResultSubscriber(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @PostConstruct
    public void init() {
        String projectId = "app-springboot-project";
        String subscriptionId = "app-ecore-consumer-sub";
        subscribe(projectId, subscriptionId);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void subscribe(String projectId, String subscriptionId) {
        try {
            CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(GoogleCredentials.fromStream(new FileInputStream(
                    "src/main/resources/keys/appubsub-admin-springboot-project-03da67dd523b.json")));

            ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
            Subscriber subscriber = Subscriber.newBuilder(subscriptionName, (MessageReceiver) (message, consumer) -> {
            String jsonData = message.getData().toStringUtf8();
                try {
                    FaceDetectionMessage faceDetectionMessage = objectMapper.readValue(jsonData, FaceDetectionMessage.class);
                    eventBus.publish(faceDetectionMessage);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                consumer.ack();
            }).setCredentialsProvider(credentialsProvider).build();
            subscriber.startAsync().awaitRunning();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

