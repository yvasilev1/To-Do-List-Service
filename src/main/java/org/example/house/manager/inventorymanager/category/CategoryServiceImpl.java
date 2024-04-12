package org.example.house.manager.inventorymanager.category;

import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.inventorymanager.category.dao.CategoryRepository;
import org.example.house.manager.inventorymanager.category.domain.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> createCategory(Category category) {
        return !categoryRepository.existsByName(category.getName()) ? Optional.of(categoryRepository.save(category)) : categoryRepository.findByCategoryName(category.getName());
    }

    @Override
    public Optional<Category> updateCategoryByName(String newCategoryName, String categoryName) {

        int success = categoryRepository.updateCategoryByName(newCategoryName, categoryName);

        if (success == 1) {
            return categoryRepository.findByCategoryName(newCategoryName);
        }

        throw new NotFoundException(new ErrorResponse(404, "No category found with provided name"));
    }

    @Override
    public int deleteCategoryByName(String categoryName) {
        return categoryRepository.deleteCategoryByName(categoryName);
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        Optional<Category> category = categoryRepository.findByCategoryName(categoryName);

        if (category.isPresent()) {
            return category.get();
        }

        throw new NotFoundException(new ErrorResponse(404, "No category found with provided name"));
    }

    @Override
    public List<Category> getCategories() {
        Iterable<Category> categories = categoryRepository.findAll();
        return StreamSupport.stream(categories.spliterator(), false).toList();
    }
}
