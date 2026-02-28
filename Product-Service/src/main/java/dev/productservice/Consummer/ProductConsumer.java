package dev.productservice.Consummer;

import dev.productservice.Service.ProductService;
import dev.productservice.dto.ProductCheckEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "${topic.product-check}", groupId = "product-service-group")
    public void listen(ProductCheckEvent event) {
        log.info("Vérification stock pour commande {} produit {} quantité {}",
                event.commandId(), event.product(), event.quantity());

        boolean available = productService.checkAndReduceStock(event.product(), event.quantity());

        if (available) {
            log.info("Stock suffisant pour commande {}", event.commandId());
        } else {
            log.warn("Stock insuffisant pour commande {}", event.commandId());
        }
    }}
