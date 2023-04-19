package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Categoria;

public interface CategoriaService {
    Categoria buscaCategoriaPeloTitulo(String titulo);
}
