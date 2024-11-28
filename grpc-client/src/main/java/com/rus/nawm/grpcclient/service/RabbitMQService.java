package com.rus.nawm.grpcclient.service;

import com.rus.nawm.grpcclient.DocumentCheck;
import com.rus.nawm.grpcclient.DocumentValidatorGrpc;
import com.rus.nawm.grpcclient.domain.Document;
import com.rus.nawm.grpcclient.util.DocumentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RabbitMQService {
  @Value("${document_verification.queue.name}")
  private String documentVerificationQueueName;

  @Value("${document_verification_results.queue.routingKey}")
  private String documentVerificationResultsQueueRoutingKey;

  @Value("${spring.rabbitmq.directExchangeName}")
  private String directExchangeName;

  @GrpcClient("documentValidator")
  private DocumentValidatorGrpc.DocumentValidatorBlockingStub stub;

  private final DocumentMapper documentMapper;
  private final RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = {"${document_verification.queue.name}"})
  public void receive(Document document) {
    log.info("Received message ${} from queue {}", document, documentVerificationQueueName);
    DocumentCheck.ValidateDocumentRequest request = DocumentCheck.ValidateDocumentRequest.newBuilder()
            .setDocument(documentMapper.toProtoDocument(document))
            .build();

    document.setIsValid(stub.validateDocument(request).getIsValid());
    log.info("Send document {} with result {} to rabbit", document.getId(), document.getIsValid());
    rabbitTemplate.convertAndSend(directExchangeName, documentVerificationResultsQueueRoutingKey, document);
    //todo сделать обработку результата в мейне чтобы он сохранял статус валидации + повесить слушателя на нотифаера чтобы он тоже был в курсе результатов проверки документов
  }
}
