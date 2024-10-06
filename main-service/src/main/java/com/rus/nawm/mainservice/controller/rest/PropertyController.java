package com.rus.nawm.mainservice.controller.rest;

import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public ResponseEntity<EntityModel<Property>> createProperty(@RequestBody Property property) {
        Property createdProperty = propertyService.createProperty(property);
        EntityModel<Property> entityModel = EntityModel.of(createdProperty);
        entityModel.add(linkTo(methodOn(PropertyController.class).getPropertyById(createdProperty.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(PropertyController.class).getAllProperties()).withRel("properties"));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Property>> getPropertyById(@PathVariable String id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        if (property.isPresent()) {
            EntityModel<Property> entityModel = EntityModel.of(property.get());
            entityModel.add(linkTo(methodOn(PropertyController.class).getPropertyById(id)).withSelfRel());
            entityModel.add(linkTo(methodOn(PropertyController.class).getAllProperties()).withRel("properties"));
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Property>>> getAllProperties() {
        List<EntityModel<Property>> properties = propertyService.getAllProperties()
                .stream()
                .map(property -> {
                    EntityModel<Property> entityModel = EntityModel.of(property);
                    entityModel.add(linkTo(methodOn(PropertyController.class).getPropertyById(property.getId())).withSelfRel());
                    return entityModel;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(properties);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Property>> updateProperty(@PathVariable String id, @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(property);
        EntityModel<Property> entityModel = EntityModel.of(updatedProperty);
        entityModel.add(linkTo(methodOn(PropertyController.class).getPropertyById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(PropertyController.class).getAllProperties()).withRel("properties"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}