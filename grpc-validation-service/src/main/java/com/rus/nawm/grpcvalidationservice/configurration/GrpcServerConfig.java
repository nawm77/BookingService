package com.rus.nawm.grpcvalidationservice.configurration;

import com.rus.nawm.grpcvalidationservice.service.ValidationService;
import com.rus.nawm.grpcvalidationservice.service.grpc.ValidationGrpcService;
import com.rus.nawm.grpcvalidationservice.util.DocumentMapper;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {
  private final ValidationService validationService;
  private final DocumentMapper documentMapper;

  @Bean
  public GrpcServerConfigurer grpcServerConfigurer() {
    return serverBuilder -> {
      serverBuilder.addService(new ValidationGrpcService(validationService, documentMapper));
    };
  }
}
