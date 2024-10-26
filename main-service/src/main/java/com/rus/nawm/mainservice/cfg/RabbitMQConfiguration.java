package com.rus.nawm.mainservice.cfg;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
  @Value("${audit_booking.queue.name}")
  private String auditEventQueue;

  @Value("${document_verification.queue.name}")
  private String documentVerificationQueue;

  @Value("${spring.rabbitmq.directExchangeName}")
  private String directExchangeName;

  @Value("${audit_booking.queue.routingKey}")
  private String auditRoutingKey;

  @Value("${document_verification.queue.routingKey}")
  private String documentVerificationRoutingKey;

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(directExchangeName);
  }

  @Bean
  public Queue auditEventQueue() {
    return new Queue(auditEventQueue, true);
  }

  @Bean
  public Binding auditBinding(Queue auditEventQueue, DirectExchange exchange) {
    return BindingBuilder.bind(auditEventQueue).to(exchange).with(auditRoutingKey);
  }

  @Bean
  public Queue documentApprovalQueue() {
    return new Queue(documentVerificationQueue, true);
  }

  @Bean
  public Binding documentApprovalBinding(Queue documentApprovalQueue, DirectExchange exchange) {
    return BindingBuilder.bind(documentApprovalQueue).to(exchange).with(documentVerificationRoutingKey);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
