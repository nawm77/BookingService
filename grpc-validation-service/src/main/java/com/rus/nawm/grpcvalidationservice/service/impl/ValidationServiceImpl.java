package com.rus.nawm.grpcvalidationservice.service.impl;

import com.rus.nawm.grpcvalidationservice.domain.Document;
import com.rus.nawm.grpcvalidationservice.service.ValidationService;
import com.rus.nawm.grpcvalidationservice.service.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
  private final Validator externalValidatorClient;

  @Override
  public boolean validateDocument(Document document) {
    log.info("Start validate document {}", document.getId());
    boolean result = externalValidatorClient.validate(document);
    log.info("Result for validation {} : {}", document.getId(), result);
    return result;
  }
}
