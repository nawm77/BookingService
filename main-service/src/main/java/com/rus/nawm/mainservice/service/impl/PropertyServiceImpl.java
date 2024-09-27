package com.rus.nawm.mainservice.service.impl;

import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.repository.PropertyRepository;
import com.rus.nawm.mainservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public Optional<Property> getPropertyById(String id) {
        return propertyRepository.findById(id);
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public Property updateProperty(String id, Property property) {
        return propertyRepository.findById(id).map(existingProperty -> {
            existingProperty.setAddress(property.getAddress());
            existingProperty.setOwner(property.getOwner());
            existingProperty.setPricePerNight(property.getPricePerNight());
            existingProperty.setCurrency(property.getCurrency());
            existingProperty.setAvailableFrom(property.getAvailableFrom());
            existingProperty.setAvailableTo(property.getAvailableTo());
            return propertyRepository.save(existingProperty);
        }).orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
    }

    @Override
    public void deleteProperty(String id) {
        propertyRepository.deleteById(id);
    }
}