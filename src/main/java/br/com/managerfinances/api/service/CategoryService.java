package br.com.managerfinances.api.service;


import br.com.managerfinances.api.bean.Category;
import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.dto.CategoryDTO;
import br.com.managerfinances.api.exception.BusinessException;
import br.com.managerfinances.api.exception.CategoryNotFoundException;
import br.com.managerfinances.api.repository.CategoryRepository;
import br.com.managerfinances.api.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SecurityUtils securityUtils;

    public Category createCategory(Category categoryModel) {
        try {
            User currentUser = securityUtils.getCurrentUser();

            Category category = Category.builder()
                    .name(categoryModel.getName())
                    .color(categoryModel.getColor())
                    .expense(categoryModel.getExpense())
                    .user(currentUser)
                    .build();

            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new BusinessException("Um ou mais dados de categoria invalidos");
        }
    }


    public CategoryDTO getCategoryByName(String name) {
        User currentUser = securityUtils.getCurrentUser();
        return categoryRepository.findByNameAndUser(name, currentUser).map(category ->
                        CategoryDTO.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .color(category.getColor())
                                .expense(category.getExpense())
                                .build())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));
    }


    public List<CategoryDTO> getCategories() {
        User currentUser = securityUtils.getCurrentUser();
        return categoryRepository.findByUser(currentUser).stream()
                .map(category -> CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .color(category.getColor())
                        .expense(category.getExpense())
                        .build())
                .toList();
    }
}
