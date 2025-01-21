package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {


    @Autowired
    private CategoryService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/categories")
    public ResponseEntity<Object> create(@Valid @RequestBody Category categoryModel) {
        try {
            Category category = service.createCategory(categoryModel);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(){
        try {

            Set<Category> list = service.getCategories();
            return ResponseEntity.of(Optional.of(list));
        }catch (Exception e){
            log.error("Falha ao buscar categorias {}", String.valueOf(e));
        return   ResponseEntity.badRequest().body("Falha ao buscar categorias " + e.getMessage());
        }

    }
}
