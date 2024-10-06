package com.rus.nawm.mainservice.controller.graphql;

import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PropertyControllerGraphQL {
  private final PropertyService propertyService;

  @MutationMapping
  public Property createProperty(@Argument("input") @Valid Property property) {
    return propertyService.createProperty(property);
  }

  @MutationMapping
  public Property updateProperty(@Argument("input") @Valid Property property) {
    return propertyService.updateProperty(property);
  }

  @QueryMapping
  public List<Property> getProperties() {
    return propertyService.getAllProperties();
  }

  @QueryMapping
  public Property getProperty(@Argument("id") String id) {
    return propertyService.getPropertyById(id).orElseThrow(() -> new IllegalArgumentException("Property not found"));
  }
}