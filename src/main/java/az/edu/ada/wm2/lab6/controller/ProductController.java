package az.edu.ada.wm2.lab6.controller;

import az.edu.ada.wm2.lab6.model.dto.ProductRequestDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponseDto updateProduct(@PathVariable UUID id, @RequestBody ProductRequestDto product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/expiring-before")
    public List<ProductResponseDto> getProductsExpiringBefore(@RequestParam LocalDate date) {
        return productService.getProductsExpiringBefore(date);
    }

    @GetMapping("/price-range")
    public List<ProductResponseDto> getProductsByPriceRange(@RequestParam BigDecimal minPrice,
                                                            @RequestParam BigDecimal maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }
}
