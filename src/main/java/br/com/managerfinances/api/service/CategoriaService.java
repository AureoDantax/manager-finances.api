package br.com.managerfinances.api.service;


import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.CategoriaNotFoundException;
import br.com.managerfinances.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria criaCategoria(Categoria categoriaModel) {
        try {
            Categoria categoriaCriada = Categoria.builder()
                    .titulo(categoriaModel.getTitulo())
                    .tag(categoriaModel.getTag())
                    .cor(categoriaModel.getCor())
                    .despesa(categoriaModel.getDespesa())
                    .build();
            if (categoriaRepository.existsByTag(categoriaModel.getTag())) {
                throw new RuntimeException("Esse tipo de Categoria já existe");
            }

            categoriaRepository.save(categoriaCriada);
            return categoriaCriada;
        } catch (Exception e) {
            throw new BusinessException("Um ou mais dados de categoria invalidos");
        }
    }


    public Categoria buscaCategoriaPeloTitulo(String titulo) {
        return categoriaRepository.findByTitulo(titulo)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));
    }

    public Set<Object> listaCategorias() {
        Set<Object> categorias = new HashSet<>();

        for (Categoria categoria : categoriaRepository.findAll()) {
            categorias.add(categoria);

            
        }

        return categorias;
    }
}
