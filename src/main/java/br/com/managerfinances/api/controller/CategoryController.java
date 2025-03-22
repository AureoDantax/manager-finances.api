package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {


    @Autowired
    private CategoryService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/categoria/create")
    public ResponseEntity<Object> criaCategoriaObj(@Valid @RequestBody Category categoryModel) {
        try {
            Category category = service.criaCategoria(categoryModel);
            return ResponseEntity.ok(category);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/categoria/list")
    public ResponseEntity<Object> listaCategoria() {
        try {

            List<Category> categories = service.list();

            return ResponseEntity.of(Optional.of(categories));
        } catch (Exception e) {
            log.error("Erro ao Buscar categorias {}", String.valueOf(e));
            return ResponseEntity.badRequest().body("Erro ao buscar categorias " + e.getMessage());
        }

    }
}
