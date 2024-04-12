package org.example.house.manager.inventorymanager.quantity;

import org.example.house.manager.inventorymanager.quantity.domain.QuantityType;

import java.util.List;
import java.util.Optional;

public interface QuantityTypeService {

    Optional<QuantityType> createQuantityType(QuantityType quantityType);

    Optional<QuantityType> updateQuantityTypeByName(String newName, String name);

    QuantityType getQuantityTypeByName(String name);

    List<QuantityType> getAllQuantityTypes();

    int deleteQuantityTypeByName(String name);
}
