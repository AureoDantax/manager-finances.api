package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.ItemNotFoundException;
import br.com.managerfinances.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
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
    @PostMapping("/transactions")
    @ResponseBody
    public ResponseEntity<Object> createItemObj(@Valid @RequestBody Transaction transactionModel) {
        try {
            Transaction transaction = service.createTransaction(transactionModel);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String,BigDecimal>> getBalance() throws RuntimeException {
        return ResponseEntity.ok(service.balanceCalculate());
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @GetMapping("/transactions/{name}")
    public ResponseEntity<Transaction> getransactionByName(@PathVariable String name) throws RuntimeException {
        try {

            Transaction transaction = service.getransactionByName(name);
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
