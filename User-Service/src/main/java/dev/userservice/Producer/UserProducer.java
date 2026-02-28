package dev.userservice.Producer;

import dev.userservice.dto.CommandSubmittedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProducer {

    private final KafkaTemplate<String, CommandSubmittedEvent> kafkaTemplate;

    private final String topic = "command-topic";

    public void publish(CommandSubmittedEvent event) {
        kafkaTemplate.send(topic, event.userId().toString(), event);
    }
}