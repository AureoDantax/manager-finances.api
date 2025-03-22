package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {
    List<Category> findByUser(User user);
    Optional<Category> findByNameAndUser(String name, User user);
}
