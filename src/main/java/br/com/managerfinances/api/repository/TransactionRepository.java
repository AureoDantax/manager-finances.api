package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Transaction;
import br.com.managerfinances.api.bean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndCategory_Id(User user, UUID categoryId);
    Optional<Transaction> findByDescriptionAndUser(String description, User user);

}
