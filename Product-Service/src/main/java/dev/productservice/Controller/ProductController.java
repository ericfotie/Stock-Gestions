package dev.productservice.Controller;

import dev.productservice.Producer.ProductProducer;
import dev.productservice.Service.ProductService;
import dev.productservice.dto.ProductAvailabilityEvent;
import dev.productservice.dto.ProductRequestDto;
import dev.productservice.dto.ProductResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    private final ProductService productService;
    private final ProductProducer productProducer;

    public ProductController(ProductService productService, ProductProducer productProducer) {
        this.productService = productService;
        this.productProducer = productProducer;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody ProductRequestDto dto) {
        return productService.save(dto);
    }


    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody ProductRequestDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }


    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }


    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }


    @PostMapping("/check-stock")
    public void checkStockAndNotify(@RequestParam Long commandId,
                                    @RequestParam String product,
                                    @RequestParam Integer quantity) {
        boolean available = productService.checkAndReduceStock(product, quantity);


        ProductAvailabilityEvent event = new ProductAvailabilityEvent(commandId, product, available);
        productProducer.sendProductAvailability(event);
    }}