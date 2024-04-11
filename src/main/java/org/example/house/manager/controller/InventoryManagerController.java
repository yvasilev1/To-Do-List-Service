package org.example.house.manager.controller;

import org.example.house.manager.inventorymanager.InventoryManager;
import org.example.house.manager.inventorymanager.domain.Location;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/inventorymanager")
public class InventoryManagerController {

    private final InventoryManager inventoryManager;

    public InventoryManagerController(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @GetMapping(value = "/getLocations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Location> getLocations() {
        return inventoryManager.getLocations();
    }

}
