package org.example.house.manager.inventorymanager.location;

import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.inventorymanager.location.dao.LocationRepository;
import org.example.house.manager.inventorymanager.location.domain.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<Location> addLocation(Location location) {
        return !locationRepository.existsByName(location.getName()) ? Optional.of(locationRepository.save(location)) : locationRepository.findByLocationName(location.getName());
    }

    @Override
    public int removeLocationByName(String locationName) {
        return locationRepository.deleteLocationByName(locationName);
    }

    @Override
    public Optional<Location> updateLocationByName(String newLocationName, String locationName) {
        int updated = locationRepository.updateByLocationName(newLocationName, locationName);

        if (updated == 1) {
            return locationRepository.findByLocationName(newLocationName);
        }

        throw new NotFoundException(new ErrorResponse(404, "No location found with provided name"));
    }

    @Override
    public Location getLocationByName(String locationName) {

        Optional<Location> location = locationRepository.findByLocationName(locationName);

        if (location.isPresent()) {
            return location.get();
        }

        throw new NotFoundException(new ErrorResponse(404, "No location found with provided name"));

    }

    @Override
    public List<Location> getLocations() {
        Iterable<Location> locations = locationRepository.findAll();
        return StreamSupport.stream(locations.spliterator(), false).toList();
    }

}
