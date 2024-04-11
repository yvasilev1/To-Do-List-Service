package org.example.house.manager.inventorymanager.dao;

import org.example.house.manager.inventorymanager.domain.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID>, JpaSpecificationExecutor<Category> {
}