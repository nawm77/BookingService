package com.rus.nawm.grpcvalidationservice.service;

import com.rus.nawm.grpcvalidationservice.domain.Document;

public interface Validator {
  Boolean validate(Document document);
}
