package com.rus.nawm.mainservice.service;

import com.rus.nawm.mainservice.domain.Property;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    Property createProperty(Property property);
    Optional<Property> getPropertyById(String id);
    List<Property> getAllProperties();
    Property updateProperty(String id, Property property);
    void deleteProperty(String id);
}