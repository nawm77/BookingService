package com.rus.nawm.mainservice.service.impl;

import com.rus.nawm.mainservice.domain.Document;
import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.repository.DocumentRepository;
import com.rus.nawm.mainservice.service.DocumentService;
import com.rus.nawm.mainservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class DocumentServiceImpl implements DocumentService {
  @Value("${spring.rabbitmq.directExchangeName}")
  private String directExchangeName;

  @Value("${document_verification.queue.routingKey}")
  private String documentVerificationRoutingKey;

  private final DocumentRepository documentRepository;
  private final RabbitTemplate rabbitTemplate;
  private final UserService userService;

  @Override
  public Document getDocumentById(String id) {
    return documentRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("No such document with id " + id));
  }

  @Override
  public Document getDocumentByUserId(String userId) {
    return documentRepository.getDocumentsByUserId(userId).orElseThrow(
            () -> new NoSuchElementException("No documents for user with id " + userId));
  }

  @Override
  public Document saveDocument(Document document) {
    document = documentRepository.saveAndFlush(document);
    Optional<User> user = userService.getUserById(document.getOwner().getId());
    if(user.isPresent()) {
      document.setOwner(user.get());
      sendValidateDocumentMessage(document);
      return document;
    } else {
      throw new NoSuchElementException("No such user with id " + document.getOwner().getId());
    }
  }

  @Override
  public Document updateDocument(Document document) {
    if(documentRepository.findById(document.getId()).isPresent()) {
      return documentRepository.saveAndFlush(document);
    } else {
      throw new NoSuchElementException("No such document with id " + document.getId());
    }
  }

  private void sendValidateDocumentMessage(Document document) {
    log.info("Send validate message for document {}", document.getId());
    rabbitTemplate.convertAndSend(directExchangeName, documentVerificationRoutingKey, document);
    log.info("");
  }
}
