package az.edu.ada.wm2.lab6.service.impl;

import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.dto.ProductRequestDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Product product = productMapper.toEntity(dto);

        if (dto.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
            product.setCategories(categories.stream().collect(Collectors.toSet()));
        }

        Product saved = productRepository.save(product);
        return productMapper.toResponseDto(saved);
    }

    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow();
        return productMapper.toResponseDto(product);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    public ProductResponseDto updateProduct(UUID id, ProductRequestDto dto) {
        Product product = productRepository.findById(id).orElseThrow();

        product.setProductName(dto.getProductName());
        product.setPrice(dto.getPrice());
        product.setExpirationDate(dto.getExpirationDate());

        Product updated = productRepository.save(product);
        return productMapper.toResponseDto(updated);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public List<ProductResponseDto> getProductsExpiringBefore(java.time.LocalDate date) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getExpirationDate().isBefore(date))
                .map(productMapper::toResponseDto)
                .toList();
    }

    public List<ProductResponseDto> getProductsByPriceRange(java.math.BigDecimal min, java.math.BigDecimal max) {
        return productRepository.findAll()
                .stream()
                .filter(p -> p.getPrice().compareTo(min) >= 0 && p.getPrice().compareTo(max) <= 0)
                .map(productMapper::toResponseDto)
                .toList();
    }
}
