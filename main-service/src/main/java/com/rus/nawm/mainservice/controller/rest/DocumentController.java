package com.rus.nawm.mainservice.controller.rest;

import com.rus.nawm.mainservice.domain.Document;
import com.rus.nawm.mainservice.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentService documentService;

  @GetMapping("/{id}")
  public ResponseEntity<EntityModel<Document>> getDocumentById(@PathVariable String id) {
    return ResponseEntity.ok(toModel(documentService.getDocumentById(id)));
  }

  @PostMapping
  public ResponseEntity<EntityModel<Document>> saveDocument(@RequestBody @Valid Document document) {
    document = documentService.saveDocument(document);

    EntityModel<Document> documentEntityModel = toModel(document);

    return ResponseEntity.created(documentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(documentEntityModel);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<EntityModel<Document>> getDocumentByUserId(@PathVariable String id) {
    return ResponseEntity.ok(toModel(documentService.getDocumentByUserId(id)));
  }

  private EntityModel<Document> toModel(Document document) {
    EntityModel<Document> documentModel = EntityModel.of(document);

    Link selfLink = linkTo(methodOn(DocumentController.class).getDocumentById(document.getId())).withSelfRel();
    Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");

    documentModel.add(selfLink);
    documentModel.add(allUsersLink);

    return documentModel;
  }
}
