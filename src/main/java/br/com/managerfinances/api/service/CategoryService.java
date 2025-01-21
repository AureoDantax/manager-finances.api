package br.com.managerfinances.api.service;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.CategoryNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category categoryModel) {
        try {
            Category categoryCriada = Category.builder()
                    .name(categoryModel.getName())
                    .tag(categoryModel.getTag())
                    .color(categoryModel.getColor())
                    .expense(categoryModel.getExpense())
                    .build();
            if (categoryRepository.existsByTag(categoryModel.getTag())) {
                throw new RuntimeException("Esse tipo de Categoria já existe");
            }

            categoryRepository.save(categoryCriada);
            return categoryCriada;
        } catch (Exception e) {
            throw new BusinessException("Um ou mais dados de categoria invalidos");
        }
    }


    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

    public Set<Category> getCategories() {
        HashSet<Category> categories = new HashSet<>();

        for (Category category : categoryRepository.findAll()) {
            categories.add(category);

            
        }

        return categories;
    }
}
