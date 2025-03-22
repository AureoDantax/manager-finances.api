package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.dto.TransactionDTO;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.TransactionNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import br.com.managerfinances.api.repository.TransactionRepository;
import br.com.managerfinances.api.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private SecurityUtils securityUtils;


    public Transaction createTransaction(Transaction transactionModel) {
        try {
            User currentUser = securityUtils.getCurrentUser();

            // Get category and verify it belongs to the user
            Category category = categoryRepository.findById(transactionModel.getCategory().getId())
                    .filter(c -> c.getUser().getId().equals(currentUser.getId()))
                    .orElseThrow(() -> new BusinessException("Categoria não encontrada ou não pertence ao usuário"));

            Transaction transaction = Transaction.builder()
                    .description(transactionModel.getDescription())
                    .value(transactionModel.getValue())
                    .registerDate(transactionModel.getRegisterDate())
                    .category(category)
                    .user(currentUser)
                    .build();

            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new BusinessException("Um ou mais dados de transação inválidos: " + e.getMessage());
        }
    }

    public Transaction getransactionByName(String name) {
        var user = securityUtils.getCurrentUser();
        return transactionRepository.findByDescriptionAndUser(name, user).orElseThrow(
                () -> new TransactionNotFoundException("Transação não encontrada, tente uma pesquisa diferente"));

    }

    public List<TransactionDTO> getTransactions() {
        User currentUser = securityUtils.getCurrentUser();
        return transactionRepository.findByUser(currentUser).stream().map(transaction ->
                TransactionDTO.builder()
                        .id(transaction.getId().toString())
                        .description(transaction.getDescription()).value(transaction.getValue())
                        .date(String.valueOf(transaction.getRegisterDate()))
                        .category(transaction.getCategory().getName())
                        .build()).toList();
    }

    public List<Transaction> getTransactionsByCategory(UUID categoryId) {
        User currentUser = securityUtils.getCurrentUser();
        return transactionRepository.findByUserAndCategory_Id(currentUser, categoryId);
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
        var user = securityUtils.getCurrentUser();
        if (ObjectUtils.isEmpty(transactionRepository.findByUser(user)) || ObjectUtils.isEmpty(categoryRepository.findByUser(user))) {
            return BigDecimal.ZERO;
        }
        List<Transaction> itens = new ArrayList<>(transactionRepository.findByUser(user));
        return itens.stream().filter(transaction -> transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal getotalRevenues() {
        var user = securityUtils.getCurrentUser();
        if (ObjectUtils.isEmpty(transactionRepository.findByUser(user)) || ObjectUtils.isEmpty(categoryRepository.findByUser(user))) {
            return BigDecimal.ZERO;
        }
        List<Transaction> itens = new ArrayList<>(transactionRepository.findByUser(user));
        return itens.stream().filter(transaction -> !transaction.getCategory().getExpense())
                .map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}