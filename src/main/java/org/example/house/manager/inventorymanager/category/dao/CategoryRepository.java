package org.example.house.manager.inventorymanager.category.dao;

import org.example.house.manager.inventorymanager.category.domain.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID> {

    @Query("select c from Category c where upper(c.name) = upper(?1) order by c.name")
    Optional<Category> findByCategoryName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("update Category c set c.name = ?1 where upper(c.name) = upper(?2)")
    int updateCategoryByName(@NonNull String newName, @NonNull String name);

    @Query("select (count(c) > 0) from Category c where upper(c.name) = upper(?1)")
    boolean existsByName(@NonNull String name);

    @Transactional
    @Modifying
    @Query("delete from Category c where upper(c.name) = upper(?1)")
    int deleteCategoryByName(@NonNull String name);
}