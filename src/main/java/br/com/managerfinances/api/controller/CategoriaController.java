package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.service.CategoriaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoriaController {


    @Autowired
    private CategoriaServiceImpl service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/categoria/create")
    public ResponseEntity<Object> createCategoriaObj(@Valid @RequestBody Categoria categoriaModel) {
        try {
            Categoria categoria = service.createCategoria(categoriaModel);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
