package com.rus.nawm.auditlogservice.service

import com.rus.nawm.auditlogservice.domain.BookingEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

//todo сделать абстрактный листенер с дженериком - типом сообщения и конструктором, где будет передаваться бин того сервиса, который будет выполнять действия

@Service
class BookingEventListener(private val auditService: AuditService) {
    @Value("\${audit_booking.queue.name}")
    lateinit var auditBookingQueueName: String

    private val logger = LoggerFactory.getLogger(BookingEventListener::class.java)

    @RabbitListener(queues = ["\${audit_booking.queue.name}"])
    fun receive(message: BookingEvent) {
        logger.info("Received message $message from queue $auditBookingQueueName")
        auditService.logBookingCreation(message).also { logger.info("Send event $it to database") }
    }
}