package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.bean.Item;
import br.com.managerfinances.api.exception.ItemNotFoundException;
import br.com.managerfinances.api.service.ItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*")
public class ItemController {

    @Autowired
    private ItemService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/item/create")
    @ResponseBody
    public ResponseEntity<Object> createItemObj(@Valid @RequestBody Item itemModel) {
        try {
            Item item = service.criaItem(itemModel);
            return ResponseEntity.ok("O item " + item.getNome() + " foi criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/balanco")
    public BigDecimal buscaValores() throws RuntimeException {
        return service.calculaBalanco();
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @GetMapping("/item/buscaItem/{nome}")
    public ResponseEntity<Item> buscaItemPeloTitulo(@PathVariable String nome) throws RuntimeException {
        try {

            Item item = service.buscaItemPeloNome(nome);
            return ResponseEntity.of(Optional.of(item));
        } catch (Exception e) {
            log.error("Error ao buscar o Item: ", e);
            throw new ItemNotFoundException(e.getMessage());
        }

    }

    @GetMapping("/item/list")
    public ResponseEntity<Object> listaItem() {
        try {
            Set<Object> listaitens;

            listaitens = service.listaItens();
            return ResponseEntity.of(Optional.of(listaitens));
        } catch (Exception e) {
            log.error("Erro ao Buscar itens " + e);
            return ResponseEntity.badRequest().body("Erro ao buscar itens " + e.getMessage());
        }

    }


}
