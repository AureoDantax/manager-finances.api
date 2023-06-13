package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.Categoria;
import br.com.managerfinances.api.bean.Item;
import br.com.managerfinances.api.exception.ItemNotFoundException;
import br.com.managerfinances.api.repository.CategoriaRepository;
import br.com.managerfinances.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    CategoriaRepository categoriaRepository;


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

    public Item buscaItemPeloTitulo(String titulo) {
        return itemRepository.findByNome(titulo).orElseThrow(
                () -> new ItemNotFoundException("Item não encontrado, tente uma pesquisa diferente"));

    }

    public BigDecimal calculaBalanco() {

        BigDecimal receitas = calculaTotalReceitas();
        BigDecimal despesas = calculaTotalDespesas();
        return receitas.subtract(despesas);
    }

    private BigDecimal calculaTotalDespesas() {
        List<Item> itens = new ArrayList();
        itemRepository.findAll().forEach(itens::add);
        BigDecimal totalDespesas = itens.stream().filter(item -> item.getCategoria().getDespesa())
                .map(Item::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalDespesas;
    }


    private BigDecimal calculaTotalReceitas() {
        List<Item> itens = new ArrayList();
        itemRepository.findAll().forEach(itens::add);
        BigDecimal totalReceitas = itens.stream().filter(item -> !item.getCategoria().getDespesa())
                .map(Item::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalReceitas;
    }
}