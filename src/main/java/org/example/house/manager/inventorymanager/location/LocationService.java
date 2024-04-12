package org.example.house.manager.inventorymanager.location;

import org.example.house.manager.inventorymanager.location.domain.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<Location> addLocation(Location location);

    int removeLocationByName(String locationName);

    Optional<Location> updateLocationByName(String newLocationName, String locationName);

   Location getLocationByName(String locationName);

    List<Location> getLocations();
}
