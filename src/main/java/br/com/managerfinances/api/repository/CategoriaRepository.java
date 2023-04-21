package br.com.managerfinances.api.repository;

import br.com.managerfinances.api.bean.Categoria;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoriaRepository extends CrudRepository<Categoria, UUID> {

    boolean existsByTag(String tag);

    Optional<Categoria> findByTitulo(String titulo);
}
