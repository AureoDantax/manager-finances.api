package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.exception.ItemNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import br.com.managerfinances.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ItemService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;


    public Transaction criaItem(Transaction transactionModel) {

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
                () -> new ItemNotFoundException("Transação não encontrada, tente uma pesquisa diferente"));

    }

    public BigDecimal balanceCalculate() {

        BigDecimal revenues = getotalRevenues();
        BigDecimal despesas = getotalExpenses();
        return revenues.subtract(despesas);
    }

    private BigDecimal getotalExpenses() {
        List<Transaction> itens = new ArrayList<>();
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal getotalRevenues() {
        List<Transaction> itens = new ArrayList<>();
        transactionRepository.findAll().forEach(itens::add);
        return itens.stream().filter(transaction -> !transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Object> getransactions() {
        Set<Object> itens = new HashSet<>();

        for (Transaction transaction : transactionRepository.findAll()) {
            itens.add(transaction);


        }

        return itens;
    }
}