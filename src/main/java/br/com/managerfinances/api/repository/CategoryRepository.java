package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {


    Optional<Category> findByTitle(String title);
}
