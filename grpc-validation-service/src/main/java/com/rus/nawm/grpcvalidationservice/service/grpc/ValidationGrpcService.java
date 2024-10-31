package com.rus.nawm.grpcvalidationservice.service.grpc;

import com.rus.nawm.grpcclient.DocumentCheck;
import com.rus.nawm.grpcclient.DocumentValidatorGrpc;
import com.rus.nawm.grpcvalidationservice.domain.Document;
import com.rus.nawm.grpcvalidationservice.service.ValidationService;
import com.rus.nawm.grpcvalidationservice.util.DocumentMapper;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@GrpcService
public class ValidationGrpcService extends DocumentValidatorGrpc.DocumentValidatorImplBase {
  private final ValidationService validationService;
  private final DocumentMapper documentMapper;
  @Override
  public void validateDocument(DocumentCheck.ValidateDocumentRequest request, StreamObserver<DocumentCheck.ValidateDocumentResponse> responseObserver) {
    Document document = documentMapper.toDocument(request.getDocument());
    boolean validationResult = validationService.validateDocument(document);
    DocumentCheck.ValidateDocumentResponse response = DocumentCheck.ValidateDocumentResponse
            .newBuilder()
            .setDocument(request.getDocument())
            .setIsValid(validationResult)
            .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
