package com.rus.nawm.auditlogservice.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RabbitConfig {

    @Value("\${audit_booking.queue.name}")
    lateinit var queueName: String

    @Bean
    open fun auditEventQueue(): Queue {
        return Queue(queueName, true)
    }

    @Bean
    open fun messageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }
}