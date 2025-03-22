package br.com.managerfinances.api.service;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.CategoryNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category createCategory(Category categoryModel) {
        try {
            Category category = Category.builder()
                    .name(categoryModel.getName())
                    .color(categoryModel.getColor())
                    .expense(categoryModel.getExpense())
                    .build();

            categoryRepository.save(category);
            return category;
        } catch (Exception e) {
            throw new BusinessException("Um ou mais dados de categoria invalidos");
        }
    }


    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }

    public List<Category> getCategories() {

        return (List<Category>) categoryRepository.findAll();
    }
}
