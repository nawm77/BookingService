package com.rus.nawm.grpcvalidationservice.service.impl;

import com.rus.nawm.grpcvalidationservice.domain.Document;
import com.rus.nawm.grpcvalidationservice.service.ValidationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class ValidationServiceImpl implements ValidationService {

  @Override
  public boolean validateDocument(Document document) {
    log.info("Start validate document {}", document.getId());
    boolean result = validationStub();
    log.info("Result for validation {} : {}", document.getId(), result);
    return result;
  }

  private boolean validationStub() {
    boolean result = false;
    try {
      int delay = 1000 + new Random().nextInt(4000);
      Thread.sleep(delay);
      result = new Random().nextBoolean();
      log.info("Delay: {}ms, result: {}", delay, result);
    } catch (InterruptedException e) {
      log.info(e.getMessage());
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }
    return result;
  }
}
