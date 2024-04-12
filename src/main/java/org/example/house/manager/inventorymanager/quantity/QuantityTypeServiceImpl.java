package org.example.house.manager.inventorymanager.quantity;

import org.example.house.manager.error.ErrorResponse;
import org.example.house.manager.error.NotFoundException;
import org.example.house.manager.inventorymanager.quantity.dao.QuantityTypeRepository;
import org.example.house.manager.inventorymanager.quantity.domain.QuantityType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class QuantityTypeServiceImpl implements QuantityTypeService {

    QuantityTypeRepository quantityTypeRepository;

    public QuantityTypeServiceImpl(QuantityTypeRepository quantityTypeRepository) {
        this.quantityTypeRepository = quantityTypeRepository;
    }

    @Override
    public Optional<QuantityType> createQuantityType(QuantityType quantityType) {
        return !quantityTypeRepository.existsByName(quantityType.getName()) ? Optional.of(quantityTypeRepository.save(quantityType)) : quantityTypeRepository.findQuantityTypeByName(quantityType.getName());
    }

    @Override
    public Optional<QuantityType> updateQuantityTypeByName(String newQuantityTypename, String quantityTypeName) {
        int success = quantityTypeRepository.updateQuantityTypeByName(newQuantityTypename, quantityTypeName);

        if (success == 1) {
            return quantityTypeRepository.findQuantityTypeByName(newQuantityTypename);
        }

        throw new NotFoundException(new ErrorResponse(404, "No quantity type found with provided name"));
    }

    @Override
    public QuantityType getQuantityTypeByName(String name) {
        Optional<QuantityType> optionalQuantityType = quantityTypeRepository.findQuantityTypeByName(name);

        if (optionalQuantityType.isPresent()) {
            return optionalQuantityType.get();
        }

        throw new NotFoundException(new ErrorResponse(404, "No quantity type found with provided name"));
    }

    @Override
    public List<QuantityType> getAllQuantityTypes() {
        Iterable<QuantityType> allQuantityTypes = quantityTypeRepository.findAll();
        return StreamSupport.stream(allQuantityTypes.spliterator(), false).toList();
    }

    @Override
    public int deleteQuantityTypeByName(String name) {
        return quantityTypeRepository.deleteQuantityTypeByName(name);
    }
}
