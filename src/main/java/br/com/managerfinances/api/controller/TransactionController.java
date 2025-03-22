package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.dto.TransactionDTO;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.TransactionNotFoundException;
import br.com.managerfinances.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(value = "*")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> createTransaction(@Valid @RequestBody Transaction transactionModel) {
        try {
            Transaction transaction = service.createTransaction(transactionModel);
            var transactionResponse = TransactionDTO.builder()
                    .id(transaction.getId().toString())
                    .description(transaction.getDescription())
                    .value(transaction.getValue())
                    .date(String.valueOf(transaction.getRegisterDate()))
                    .category(transaction.getCategory().getName())
                    .build();
            return ResponseEntity.ok(transactionResponse);
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, BigDecimal>> getBalance() throws RuntimeException {
        return ResponseEntity.ok(service.balanceCalculate());
    }

    @ResponseStatus(value = HttpStatus.FOUND)
    @GetMapping("/{name}")
    public ResponseEntity<Transaction> getransactionByName(@PathVariable String name) throws BusinessException {
        try {

            Transaction transaction = service.getransactionByName(name);
            return ResponseEntity.of(Optional.of(transaction));
        } catch (BusinessException e) {
            log.error("Falha ao buscar a transação: ", e);
            throw new TransactionNotFoundException(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<Object> getTransactions() {
        try {
            List<TransactionDTO> transactions = service.getTransactions();
            return ResponseEntity.of(Optional.of(transactions));
        } catch (Exception e) {
            log.error("Falha ao buscar transações " + e);
            return ResponseEntity.badRequest().body("Falha ao buscar transações " + e.getMessage());
        }

    }


}
