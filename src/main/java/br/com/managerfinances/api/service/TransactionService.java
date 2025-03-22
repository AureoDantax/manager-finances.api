package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.TransactionNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import br.com.managerfinances.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public Transaction createTransaction(Transaction transactionModel) {

        Optional<Category> categoria = categoryRepository.findById(transactionModel.getCategory().getId());

        Transaction transactionCriado = Transaction.builder()
                .name(transactionModel.getName())
                .category(categoria.get())
                .value(transactionModel.getValue())
                .registerDate(LocalDate.now())
                .build();
        transactionRepository.save(transactionCriado);
        return transactionCriado;

    }

    public Transaction getransactionByName(String name) {
        return transactionRepository.findByName(name).orElseThrow(
                () -> new TransactionNotFoundException("Transação não encontrada, tente uma pesquisa diferente"));

    }

    public Map<String, BigDecimal> balanceCalculate() {

        BigDecimal revenues = getotalRevenues();
        BigDecimal expenses = getotalExpenses();
        BigDecimal amount = getAmount(revenues, expenses);
        return Map.of("revenues", revenues, "expenses", expenses, "amount", amount);
    }

    private BigDecimal getAmount(BigDecimal revenues, BigDecimal expenses) {
        return revenues.subtract(expenses);
    }

    private BigDecimal getotalExpenses() {
        List<Transaction> itens = new ArrayList<>();
        if (ObjectUtils.isEmpty(transactionRepository.findAll())) {
            return BigDecimal.ZERO;
        }
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal getotalRevenues() {
        List<Transaction> itens = new ArrayList<>();
        if (ObjectUtils.isEmpty(transactionRepository.findAll())) {
            return BigDecimal.ZERO;
        }
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> !transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Object> getransactions() {
        List<Object> itens = new ArrayList<>();
        transactionRepository.findAll().forEach(itens::add);
        return itens;
    }
}