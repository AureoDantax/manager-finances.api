package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.TransactionNotFoundException;
import br.com.managerfinances.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(value = "*")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/item/create")
    @ResponseBody
    public ResponseEntity<Object> createItemObj(@Valid @RequestBody Transaction transactionModel) {
        try {
            Transaction transaction = service.createTransaction(transactionModel);
            return ResponseEntity.ok("O item " + transaction.getName() + " foi criado com sucesso!");
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
    public ResponseEntity<Transaction> buscaItemPeloTitulo(@PathVariable String nome) throws RuntimeException {
        try {

            Transaction transaction = service.getTransactionByName(nome);
            return ResponseEntity.of(Optional.of(transaction));
        } catch (Exception e) {
            log.error("Error ao buscar o Item: ", e);
            throw new TransactionNotFoundException(e.getMessage());
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
