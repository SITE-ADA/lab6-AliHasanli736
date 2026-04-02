package az.edu.ada.wm2.lab6.controller;

import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto) {
        return categoryService.create(dto);
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public CategoryResponseDto addProduct(@PathVariable UUID categoryId, @PathVariable UUID productId) {
        return categoryService.addProduct(categoryId, productId);
    }

    @GetMapping("/{categoryId}/products")
    public List<ProductResponseDto> getProducts(@PathVariable UUID categoryId) {
        return categoryService.getProducts(categoryId);
    }
}
