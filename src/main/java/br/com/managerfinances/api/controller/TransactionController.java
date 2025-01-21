package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.ItemNotFoundException;
import br.com.managerfinances.api.service.ItemService;
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
    private ItemService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/transactions")
    @ResponseBody
    public ResponseEntity<Object> createItemObj(@Valid @RequestBody Transaction transactionModel) {
        try {
            Transaction transaction = service.criaItem(transactionModel);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/balance")
    public BigDecimal buscaValores() throws RuntimeException {
        return service.balanceCalculate();
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @GetMapping("/transactions/{nome}")
    public ResponseEntity<Transaction> getransactionByName(@PathVariable String nome) throws RuntimeException {
        try {

            Transaction transaction = service.getransactionByName(nome);
            return ResponseEntity.of(Optional.of(transaction));
        } catch (Exception e) {
            log.error("Falha ao buscar a transação: ", e);
            throw new ItemNotFoundException(e.getMessage());
        }

    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> getTransactions() {
        try {
            Set<Object> listaitens = service.getransactions();
            return ResponseEntity.of(Optional.of(listaitens));
        } catch (Exception e) {
            log.error("Falha ao buscar transações " + e);
            return ResponseEntity.badRequest().body("Falha ao buscar transações " + e.getMessage());
        }

    }


}
