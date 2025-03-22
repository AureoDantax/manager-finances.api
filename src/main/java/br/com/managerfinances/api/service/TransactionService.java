package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.TransactionNotFoundException;
import br.com.managerfinances.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryService categoryService;



    public Transaction createTransaction(Transaction transactionModel) {

        Category category = categoryService.getCategoryByTitle(transactionModel.getCategory().getTitle());

        Transaction transaction = Transaction.builder()
                .name(transactionModel.getName())
                .category(category)
                .value(transactionModel.getValue())
                .registerDate(LocalDate.now())
                .build();
        transactionRepository.save(transaction);
        return transaction;

    }

    public Transaction getTransactionByName(String name) {
        return transactionRepository.findByName(name).orElseThrow(
                () -> new TransactionNotFoundException("Item não encontrado, tente uma pesquisa diferente"));

    }

    public BigDecimal calculaBalanco() {

        BigDecimal incomes = calculateIncomes();
        BigDecimal expenses = calculateExpenses();
        return incomes.subtract(expenses);
    }

    private BigDecimal calculateExpenses() {
        List<Transaction> itens = new ArrayList<>();
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal calculateIncomes() {
        List<Transaction> itens = new ArrayList<>();
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> !transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Object> listaItens() {
        Set<Object> itens = new HashSet<>();

        for (Transaction transaction : transactionRepository.findAll()) {
            itens.add(transaction);


        }

        return itens;
    }
}