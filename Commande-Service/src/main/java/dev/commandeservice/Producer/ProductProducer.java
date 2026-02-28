package dev.commandeservice.Producer;

import dev.commandeservice.dto.ProductCheckEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductProducer {
   @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topic.product-check}")
    private String topic;

    public ProductProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductCheck(ProductCheckEvent event) {

        kafkaTemplate.send(topic, String.valueOf(event.commandId()), event);
        log.info("Événement ProductCheck envoyé pour commande {} produit {}",
                event.commandId(), event.product());
    }
}