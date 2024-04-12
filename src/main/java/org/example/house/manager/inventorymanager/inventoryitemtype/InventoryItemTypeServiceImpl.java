package org.example.house.manager.inventorymanager.inventoryitemtype;

import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.inventorymanager.inventoryitemtype.dao.InventoryItemTypeRepository;
import org.example.house.manager.inventorymanager.inventoryitemtype.domain.InventoryItemType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class InventoryItemTypeServiceImpl implements InventoryItemTypeService {

    private final InventoryItemTypeRepository inventoryItemTypeRepository;

    public InventoryItemTypeServiceImpl(InventoryItemTypeRepository inventoryItemTypeRepository) {
        this.inventoryItemTypeRepository = inventoryItemTypeRepository;
    }

    @Override
    public Optional<InventoryItemType> createInventoryItemType(InventoryItemType inventoryItemType) {
        return !inventoryItemTypeRepository.existsByName(inventoryItemType.getName()) ? Optional.of(inventoryItemTypeRepository.save(inventoryItemType)) : inventoryItemTypeRepository.findInventoryItemTypeByName(inventoryItemType.getName());
    }

    @Override
    public Optional<InventoryItemType> updateInventoryItemTypeByName(String newInventoryItemType, String inventoryItemTypeName) {
        int success = inventoryItemTypeRepository.updateInventoryItemTypeByName(newInventoryItemType, inventoryItemTypeName);

        if (success == 1) {
            return inventoryItemTypeRepository.findInventoryItemTypeByName(newInventoryItemType);
        }

        throw new NotFoundException(new ErrorResponse(404, "No inventory item type found with provided name"));
    }

    @Override
    public int deleteInventoryItemTypeByName(String name) {
        return inventoryItemTypeRepository.deleteInventoryItemTypeByName(name);
    }

    @Override
    public InventoryItemType findInventoryItemTypeByName(String name) {
        Optional<InventoryItemType> inventoryItemTypeOptional = inventoryItemTypeRepository.findInventoryItemTypeByName(name);

        if (inventoryItemTypeOptional.isPresent()) {
            return inventoryItemTypeOptional.get();
        }

        throw new NotFoundException(new ErrorResponse(404, "No inventory item type found with provided name"));
    }

    @Override
    public List<InventoryItemType> findAllInventoryItemTypes() {
        Iterable<InventoryItemType> inventoryItemTypes = inventoryItemTypeRepository.findAll();
        return StreamSupport.stream(inventoryItemTypes.spliterator(), false).toList();
    }
}
