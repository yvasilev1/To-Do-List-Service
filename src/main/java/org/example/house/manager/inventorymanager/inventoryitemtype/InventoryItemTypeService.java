package org.example.house.manager.inventorymanager.inventoryitemtype;

import org.example.house.manager.inventorymanager.inventoryitemtype.domain.InventoryItemType;

import java.util.List;
import java.util.Optional;

public interface InventoryItemTypeService {

    Optional <InventoryItemType> createInventoryItemType(InventoryItemType inventoryItemType);

    Optional<InventoryItemType> updateInventoryItemTypeByName(String newName, String name);

    int deleteInventoryItemTypeByName(String name);

    InventoryItemType findInventoryItemTypeByName(String name);

    List<InventoryItemType> findAllInventoryItemTypes();
}
