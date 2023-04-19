package br.com.managerfinances.api.service;


import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.exception.CategoriaNotFoundException;
import br.com.managerfinances.api.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public Categoria createCategoria(Categoria categoriaModel){
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
            }catch (Exception e){
                throw new RuntimeException("Um ou mais dados de categoria invalidos");
            }
    }

    @Override
    public Categoria buscaCategoriaPeloTitulo(String titulo) {
        return categoriaRepository.findByTitulo(titulo)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada"));

    }
}
