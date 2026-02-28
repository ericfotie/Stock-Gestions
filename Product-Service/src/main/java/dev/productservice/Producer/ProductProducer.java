package dev.productservice.Producer;

import dev.productservice.dto.ProductAvailabilityEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topic.product-availability}")
    private String topic;

    public ProductProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductAvailability(ProductAvailabilityEvent event) {
        kafkaTemplate.send(topic, event.product(), event);
        log.info("Événement ProductAvailability envoyé pour commande {} produit {} disponible ? {}",
                event.commandId(), event.product(), event.available());
    }
}