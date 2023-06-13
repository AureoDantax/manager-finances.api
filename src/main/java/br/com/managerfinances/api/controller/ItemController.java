package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Item;
import br.com.managerfinances.api.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/balanco")
    public BigDecimal buscaValores(){
        BigDecimal valorBalanco = service.calculaBalanco();
        return valorBalanco;
    }


}
