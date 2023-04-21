package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.bean.Item;
import br.com.managerfinances.api.repository.CategoriaRepository;
import br.com.managerfinances.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    CategoriaRepository categoriaRepository;


    @Override
    public Item criaItem(Item itemModel) {


        Categoria categoria = categoriaService.buscaCategoriaPeloTitulo(itemModel.getCategoria().getTitulo());

        Item itemCriado = Item.builder()
                .nome(itemModel.getNome())
                .categoria(categoria)
                .valor(itemModel.getValor())
                .dataRegistro(LocalDate.now())
                .build();
        itemRepository.save(itemCriado);
        return itemCriado;


    }


}
