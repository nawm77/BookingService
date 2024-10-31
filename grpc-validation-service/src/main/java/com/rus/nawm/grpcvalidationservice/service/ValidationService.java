package com.rus.nawm.grpcvalidationservice.service;


import com.rus.nawm.grpcvalidationservice.domain.Document;

public interface ValidationService {
  boolean validateDocument(Document document);
}
