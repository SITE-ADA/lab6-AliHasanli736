package az.edu.ada.wm2.lab6.service.impl;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository,
                               ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName(dto.getName());

        Category saved = categoryRepository.save(category);

        CategoryResponseDto response = new CategoryResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());

        return response;
    }

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> {
                    CategoryResponseDto dto = new CategoryResponseDto();
                    dto.setId(c.getId());
                    dto.setName(c.getName());
                    return dto;
                })
                .toList();
    }

    public CategoryResponseDto addProduct(UUID categoryId, UUID productId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        category.getProducts().add(product);
        categoryRepository.save(category);

        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    public List<ProductResponseDto> getProducts(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        return category.getProducts()
                .stream()
                .map(productMapper::toResponseDto)
                .toList();
    }
}
