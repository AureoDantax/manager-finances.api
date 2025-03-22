package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.dto.CategoryDTO;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.CategoryNotFoundException;
import br.com.managerfinances.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@Slf4j
public class CategoryController {


    @Autowired
    private CategoryService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Category categoryModel) {
        try {
            Category category = service.createCategory(categoryModel);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByname(@PathVariable String name) throws BusinessException {
        try {

            CategoryDTO category = service.getCategoryByName(name);
            return ResponseEntity.of(Optional.of(category));
        } catch (BusinessException e) {
            log.error("Falha ao buscar a categoria: ", e);
            throw new CategoryNotFoundException(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<Object> getCategories() {
        try {
            List<CategoryDTO> list = service.getCategories();
            return ResponseEntity.of(Optional.of(list));
        } catch (BusinessException e) {
            log.error("Falha ao buscar categorias {}", String.valueOf(e));
            return ResponseEntity.badRequest().body("Falha ao buscar categorias " + e.getMessage());
        }

    }
}
