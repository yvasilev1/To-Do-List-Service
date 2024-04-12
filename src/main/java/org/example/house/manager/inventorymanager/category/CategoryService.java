package org.example.house.manager.inventorymanager.category;

import org.example.house.manager.inventorymanager.category.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> createCategory(Category category);

    Optional<Category> updateCategoryByName(String newCategoryName, String categoryName);

    int deleteCategoryByName(String categoryName);

    Category getCategoryByName(String categoryName);

    List<Category> getCategories();

}
