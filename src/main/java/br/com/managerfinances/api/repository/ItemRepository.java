package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {
    Optional<Item> findByNome(String nome);


}
