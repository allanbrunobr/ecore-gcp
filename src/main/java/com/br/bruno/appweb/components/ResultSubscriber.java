package com.br.bruno.appweb.components;

import com.br.bruno.appweb.events.EventBus;
import com.br.bruno.appweb.models.vision.FaceDetectionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;



/**
 * The ResultSubscriber class is responsible for subscribing to different Google Pub/Sub
 * subscriptions and processing the messages received. It utilizes the EventBus class to publish
 * events based on the subscription ID.
 *
 * <p>The class requires an instance of the EventBus class to be passed in the constructor.
 * The subscribe() method is used to subscribe to a specific subscription and
 * process the received messages.
 *
 * <p>Sample usage:
 * <pre>{@code
 * EventBus eventBus = new EventBus();
 * ResultSubscriber subscriber = new ResultSubscriber(eventBus);
 * subscriber.init();
 * }</pre>
 */
@Component
public class ResultSubscriber {

  private EventBus eventBus;

  public ResultSubscriber(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  /**
   * Initializes the ResultSubscriber by subscribing to a specific Google Pub/Sub
   * subscription and processing the received messages. This method is annotated
   * with @PostConstruct, which means that it will be called automatically after
   * the ResultSubscriber bean has been constructed
   * and all the dependencies have been injected.
   *
   * <p>The method retrieves the project ID and subscription ID and
   * then calls the subscribe() method to perform the subscription
   * and message processing.
   *
   * @see ResultSubscriber#subscribe(String, String)
   */
  @PostConstruct
  public void init() {
    String projectId = "app-springboot-project";
    String subscriptionId = "app-ecore-consumer-sub";
    subscribe(projectId, subscriptionId);
  }

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Subscribes to a specific Google Pub/Sub subscription and processes the received messages.
   *
   * @param projectId       the ID of the Google Cloud project
   * @param subscriptionId  the ID of the Pub/Sub subscription
   */
  public void subscribe(String projectId, String subscriptionId) {
    try {
      CredentialsProvider credentialsProvider = FixedCredentialsProvider
              .create(GoogleCredentials.fromStream(new FileInputStream(
          "src/main/resources/keys/appubsub-admin-springboot-project-03da67dd523b.json")));

      ProjectSubscriptionName subscriptionName = ProjectSubscriptionName
                    .of(projectId, subscriptionId);
      Subscriber subscriber = Subscriber
                    .newBuilder(subscriptionName, (MessageReceiver) (message, consumer) -> {
                      String jsonData = message.getData().toStringUtf8();
                      try {
                        FaceDetectionMessage faceDetectionMessage = objectMapper
                                .readValue(jsonData, FaceDetectionMessage.class);
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

