package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class CategoriaController {


    @Autowired
    private CategoriaService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/categoria/create")
    public ResponseEntity<Object> criaCategoriaObj(@Valid @RequestBody Categoria categoriaModel) {
        try {
            Categoria categoria = service.criaCategoria(categoriaModel);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categoria/list")
    public Set <Object> buscaCategoria(){
        Set listacategorias;
       listacategorias = service.listaCategorias();
        return listacategorias;

    }
}
