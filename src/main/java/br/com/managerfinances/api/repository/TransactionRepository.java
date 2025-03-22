package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    Optional<Transaction> findByName(String name);


}
