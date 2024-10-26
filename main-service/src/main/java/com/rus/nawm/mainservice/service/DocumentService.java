package com.rus.nawm.mainservice.service;

import com.rus.nawm.mainservice.domain.Document;

public interface DocumentService {
  Document getDocumentById(String id);
  Document getDocumentByUserId(String userId);
  Document saveDocument(Document document);
  Document updateDocument(Document document);
}
