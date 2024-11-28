package com.rus.nawm.grpcvalidationservice.util;


import com.rus.nawm.grpcclient.DocumentCheck;
import com.rus.nawm.grpcvalidationservice.domain.Document;
import com.rus.nawm.grpcvalidationservice.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
  User toUser(DocumentCheck.User user);

  Document toDocument(DocumentCheck.Document document);

  DocumentCheck.Document toProtoDocument(Document document);

  DocumentCheck.User toProtoUser(User user);
}