package com.rus.nawm.auditlogservice.service

import com.rus.nawm.auditlogservice.domain.BookingEvent
import com.rus.nawm.auditlogservice.repository.AuditRepository
import org.springframework.stereotype.Service

@Service
class AuditService(private val auditRepository: AuditRepository) {

    fun logBookingCreation(bookingEvent: BookingEvent): BookingEvent {
        auditRepository.saveBookingCreationEvent(bookingEvent)
        return bookingEvent
    }

    fun logBookingCancellation(bookingEvent: BookingEvent) {
        auditRepository.saveBookingCancellationEvent(bookingEvent)
    }

    fun logBookingStatusUpdate(bookingEvent: BookingEvent) {
        auditRepository.saveBookingStatusUpdate(bookingEvent)
    }
}