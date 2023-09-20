package br.com.managerfinances.api.controller;


import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.exception.CategoriaNotFoundException;
import br.com.managerfinances.api.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Slf4j
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

    @GetMapping("/categoria/list")
    public ResponseEntity<Object> listaCategoria(){
        try {
            Set<Categoria> listacategorias;


            listacategorias = service.listaCategorias();
            return ResponseEntity.of(Optional.of(listacategorias));
        }catch (Exception e){
            log.error("Erro ao Buscar categorias " + e);
        return   ResponseEntity.badRequest().body("Erro ao buscar categorias " + e.getMessage());
        }

    }
}
