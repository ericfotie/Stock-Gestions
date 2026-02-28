package dev.productservice.Service.ServiceImpl;

import dev.productservice.Model.ProductModel;
import dev.productservice.Producer.ProductProducer;
import dev.productservice.Repository.ProductRepository;
import dev.productservice.Service.ProductService;
import dev.productservice.dto.ProductAvailabilityEvent;
import dev.productservice.dto.ProductRequestDto;
import dev.productservice.dto.ProductResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductProducer productProducer;


    @Override
    public ProductResponseDto save(ProductRequestDto dto) {
        ProductModel product = new ProductModel();
        product.setName(dto.name());
        product.setStock(dto.stock());
        ProductModel saved = productRepository.save(product);
        log.info("Produit créé : {}", saved.getName());
        return mapToDto(saved);
    }


    @Override
    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        product.setName(dto.name());
        product.setStock(dto.stock());
        ProductModel updated = productRepository.save(product);
        log.info("Produit mis à jour : {}", updated.getName());
        return mapToDto(updated);
    }


    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Produit non trouvé pour suppression");
        }
        productRepository.deleteById(id);
        log.info("Produit supprimé ID {}", id);
    }

    @Override
    public ProductResponseDto getById(Long id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return mapToDto(product);
    }

    // GET ALL
    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public boolean checkAndReduceStock(String productName, int quantity) {
        ProductModel product = productRepository.findByName(productName)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + productName));

        if (product.getStock() < quantity) {
            log.warn("Stock insuffisant pour le produit {} : demandé {}, disponible {}",
                    productName, quantity, product.getStock());
            return false;
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        log.info("Stock réduit pour {} : nouveau stock {}", productName, product.getStock());
        return true;
    }

    public void sendAvailabilityEvent(Long commandId, String productName, int quantity) {
        boolean available = checkAndReduceStock(productName, quantity);

        ProductAvailabilityEvent event = new ProductAvailabilityEvent(commandId, productName, available);
        productProducer.sendProductAvailability(event);

        log.info("Événement ProductAvailability envoyé pour commande {} produit {} disponible ? {}",
                commandId, productName, available);
    }

    // Helper to map entity -> DTO
    private ProductResponseDto mapToDto(ProductModel product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getStock());
    }
}