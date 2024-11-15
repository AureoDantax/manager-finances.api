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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Item buscaItemPeloNome(String nome) {
        return itemRepository.findByNome(nome).orElseThrow(
                () -> new ItemNotFoundException("Item não encontrado, tente uma pesquisa diferente"));

    }

    public BigDecimal calculaBalanco() {

        BigDecimal receitas = calculaTotalReceitas();
        BigDecimal despesas = calculaTotalDespesas();
        return receitas.subtract(despesas);
    }

    private BigDecimal calculaTotalDespesas() {
        List<Item> itens = new ArrayList<>();
        itemRepository.findAll().forEach(itens::add);
        return itens.stream().filter(item -> item.getCategoria().getDespesa())
                .map(Item::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal calculaTotalReceitas() {
        List<Item> itens = new ArrayList<>();
        itemRepository.findAll().forEach(itens::add);
        return itens.stream().filter(item -> !item.getCategoria().getDespesa())
                .map(Item::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Object> listaItens() {
        Set<Object> itens = new HashSet<>();

        for (Item item : itemRepository.findAll()) {
            itens.add(item);


        }

        return itens;
    }
}