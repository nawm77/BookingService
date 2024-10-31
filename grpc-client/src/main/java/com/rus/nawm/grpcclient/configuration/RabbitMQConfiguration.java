package com.rus.nawm.grpcclient.configuration;

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
  @Value("${document_verification_results.queue.name}")
  private String documentVerificationResultsQueueName;

  @Value("${document_verification_results.queue.routingKey}")
  private String documentVerificationResultsQueueRoutingKey;

  @Value("${spring.rabbitmq.directExchangeName}")
  private String directExchangeName;

  @Value("${document_verification.queue.name}")
  private String documentVerificationQueue;

  @Bean
  public DirectExchange exchange() {
    return new DirectExchange(directExchangeName);
  }

  @Bean
  public Queue documentVerificationResultsQueue() {
    return new Queue(documentVerificationResultsQueueName, true);
  }

  @Bean
  public Binding documentVerificationResultsRoutingKey(Queue documentVerificationResultsQueue, DirectExchange exchange) {
    return BindingBuilder.bind(documentVerificationResultsQueue).to(exchange).with(documentVerificationResultsQueueRoutingKey);
  }

  @Bean
  public Queue documentApprovalQueue() {
    return new Queue(documentVerificationQueue, true);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
