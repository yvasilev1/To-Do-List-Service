package org.example.house.manager.inventorymanager.dao;

import org.example.house.manager.inventorymanager.domain.InventoryItemType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryItemTypeRepository extends CrudRepository<InventoryItemType, UUID>, JpaSpecificationExecutor<InventoryItemType> {
}