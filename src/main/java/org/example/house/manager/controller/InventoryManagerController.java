package org.example.house.manager.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.inventorymanager.InventoryManager;
import org.example.house.manager.inventorymanager.category.CategoryService;
import org.example.house.manager.inventorymanager.category.domain.Category;
import org.example.house.manager.inventorymanager.inventoryitemtype.InventoryItemTypeService;
import org.example.house.manager.inventorymanager.inventoryitemtype.domain.InventoryItemType;
import org.example.house.manager.inventorymanager.location.LocationService;
import org.example.house.manager.inventorymanager.location.domain.Location;
import org.example.house.manager.inventorymanager.quantity.QuantityTypeService;
import org.example.house.manager.inventorymanager.quantity.domain.QuantityType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/inventorymanager")
public class InventoryManagerController {

    private final InventoryManager inventoryManager;
    private final LocationService locationService;
    private final CategoryService categoryService;
    private final InventoryItemTypeService inventoryItemTypeService;
    private final QuantityTypeService quantityTypeService;

    public InventoryManagerController(InventoryManager inventoryManager, LocationService locationService, CategoryService categoryService, InventoryItemTypeService inventoryItemTypeService, QuantityTypeService quantityTypeService) {
        this.inventoryManager = inventoryManager;
        this.locationService = locationService;
        this.categoryService = categoryService;
        this.inventoryItemTypeService = inventoryItemTypeService;
        this.quantityTypeService = quantityTypeService;
    }

    @PostMapping(value = "/v1/addLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Location> addLocation(@Valid @RequestBody Location location) {
        return locationService.addLocation(location);
    }

    @GetMapping(value = "/v1/getLocationByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Location getLocationByName(@PathParam(value = "name") String name) {
        return locationService.getLocationByName(name);
    }

    @DeleteMapping(value = "/v1/deleteLocationByName")
    public ResponseEntity<HttpStatus> deleteLocationByName(@PathParam(value = "name") String name) {
        int deletedLocation = locationService.removeLocationByName(name);

        if (deletedLocation == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new NotFoundException(new ErrorResponse(404, "No location found with provided name"));
    }

    @PutMapping(value = "/v1/updateLocationByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Location> updateLocationByName(@PathParam(value = "name") String name, @Valid @RequestBody Location location) {
        return locationService.updateLocationByName(location.getName(), name);
    }

    @GetMapping(value = "/v1/getLocations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Location> getLocations() {
        return locationService.getLocations();
    }

    @PostMapping(value = "/v1/addCategory", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Category> createCategory(@Valid @RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping(value = "/v1/getCategoryByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Category getCategoryByName(@PathParam(value = "name") String name) {
        return categoryService.getCategoryByName(name);
    }

    @DeleteMapping(value = "/v1/deleteCategoryByName")
    public ResponseEntity<HttpStatus> deleteCategoryByName(@PathParam(value = "name") String name) {
        int deletedCategory = categoryService.deleteCategoryByName(name);

        if (deletedCategory == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new NotFoundException(new ErrorResponse(404, "No location found with provided name"));
    }

    @PutMapping(value = "/v1/updateCategoryByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<Category> updateCategoryByName(@PathParam(value = "name") String name, @Valid @RequestBody Category category) {
        return categoryService.updateCategoryByName(category.getName(), name);
    }

    @GetMapping(value = "/v1/getCategories", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping(value = "/v1/addInventoryItemType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<InventoryItemType> addInventoryItemType(@Valid @RequestBody InventoryItemType inventoryItemType) {
        return inventoryItemTypeService.createInventoryItemType(inventoryItemType);
    }

    @GetMapping(value = "/v1/getInventoryItemTypeByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InventoryItemType getInventoryItemTypeByName(@PathParam(value = "name") String name) {
        return inventoryItemTypeService.findInventoryItemTypeByName(name);
    }

    @DeleteMapping(value = "/v1/deleteInventoryItemTypeByName")
    public ResponseEntity<HttpStatus> deleteInventoryItemTypeByName(@PathParam(value = "name") String name) {
        int deletedInventoryItemTypeByName = inventoryItemTypeService.deleteInventoryItemTypeByName(name);

        if (deletedInventoryItemTypeByName == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new NotFoundException(new ErrorResponse(404, "No inventory item type found with provided name"));
    }

    @PutMapping(value = "/v1/updateInventoryItemTypeByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<InventoryItemType> updateInventoryItemTypeByName(@PathParam(value = "name") String name, @Valid @RequestBody InventoryItemType inventoryItemType) {
        return inventoryItemTypeService.updateInventoryItemTypeByName(inventoryItemType.getName(), name);
    }

    @GetMapping(value = "/v1/getInventoryItemTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<InventoryItemType> getInventoryItemTypes() {
        return inventoryItemTypeService.findAllInventoryItemTypes();
    }

    @PostMapping(value = "/v1/addQuantityType", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<QuantityType> addQuantityType(@Valid @RequestBody QuantityType quantityType) {
        return quantityTypeService.createQuantityType(quantityType);
    }

    @GetMapping(value = "/v1/getQuantityTypeByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public QuantityType getQuantityTypeByName(@PathParam(value = "name") String name) {
        return quantityTypeService.getQuantityTypeByName(name);
    }

    @DeleteMapping(value = "/v1/deleteQuantityTypeByName")
    public ResponseEntity<HttpStatus> deleteQuantityTypeByName(@PathParam(value = "name") String name) {
        int deletedQuantityType = quantityTypeService.deleteQuantityTypeByName(name);

        if (deletedQuantityType == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new NotFoundException(new ErrorResponse(404, "No quantity type found with provided name"));
    }

    @PutMapping(value = "/v1/updateQuantityTypeByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<QuantityType> updateQuantityTypeByName(@PathParam(value = "name") String name, @Valid @RequestBody QuantityType quantityType) {
        return quantityTypeService.updateQuantityTypeByName(quantityType.getName(), name);
    }

    @GetMapping(value = "/v1/getQuantityTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<QuantityType> getQuantityTypes() {
        return quantityTypeService.getAllQuantityTypes();
    }

}
