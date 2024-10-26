package com.rus.nawm.mainservice.controller.graphql;

import com.rus.nawm.mainservice.domain.Document;
import com.rus.nawm.mainservice.service.DocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DocumentControllerGraphQL {
  private final DocumentService documentService;

  @MutationMapping
  public Document createDocument(@Argument("input") @Valid Document document) {
    return documentService.saveDocument(document);
  }

  @MutationMapping
  public Document updateDocument(@Argument("input") @Valid Document document) {
    return documentService.updateDocument(document);
  }

  @QueryMapping
  public Document getDocument(@Argument("id") String id) {
    return documentService.getDocumentById(id);
  }
}
