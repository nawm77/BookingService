package com.rus.nawm.auditlogservice.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.rus.nawm.auditlogservice.event.BookingEvent
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class BookingEventListener(private val auditService: AuditService) {
    private val logger = LoggerFactory.getLogger(BookingEventListener::class.java)

    @Value("\${audit_booking.queue.name}")
    lateinit var auditBookingQueueName: String

    @RabbitListener(queues = ["\${audit_booking.queue.name}"])
    fun receive(message: BookingEvent) {
        logger.info("Received message $message from queue $auditBookingQueueName")
//        val event: BookingEvent = parseMessage(message)
        auditService.logBookingCreation(message).also { logger.info("Send event $it to database") }
    }

    private fun parseMessage(message: String): BookingEvent {
        try {
            return ObjectMapper().readValue(message, BookingEvent::class.java)
        } catch (e: JsonProcessingException) {
            logger.warn(e.message)
            throw RuntimeException(e)
        }
    }
}