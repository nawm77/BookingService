package com.rus.nawm.grpcvalidationservice.service.impl;

import com.rus.nawm.grpcvalidationservice.domain.Document;
import com.rus.nawm.grpcvalidationservice.service.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class ValidatorStubImpl implements Validator {

  @Override
  public Boolean validate(Document document) {
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
