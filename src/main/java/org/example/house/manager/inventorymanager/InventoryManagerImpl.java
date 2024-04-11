package org.example.house.manager.inventorymanager;

import org.example.house.manager.inventorymanager.dao.CategoryRepository;
import org.example.house.manager.inventorymanager.dao.InventoryItemTypeRepository;
import org.example.house.manager.inventorymanager.dao.LocationRepository;
import org.example.house.manager.inventorymanager.dao.QuantityTypeRepository;
import org.example.house.manager.inventorymanager.domain.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InventoryManagerImpl implements InventoryManager {

    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryItemTypeRepository inventoryItemTypeRepository;
    private final QuantityTypeRepository quantityTypeRepository;

    public InventoryManagerImpl(LocationRepository locationRepository, CategoryRepository categoryRepository, InventoryItemTypeRepository inventoryItemTypeRepository, QuantityTypeRepository quantityTypeRepository) {
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.inventoryItemTypeRepository = inventoryItemTypeRepository;
        this.quantityTypeRepository = quantityTypeRepository;
    }

    @Override
    public List<Location> getLocations() {
        Iterable<Location> locations = locationRepository.findAll();
        return StreamSupport.stream(locations.spliterator(), false).collect(Collectors.toList());
    }
}
