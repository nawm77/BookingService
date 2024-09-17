package com.rus.nawm.auditlogservice.service

import com.rus.nawm.auditlogservice.event.BookingEvent
import com.rus.nawm.auditlogservice.repository.AuditRepository
import org.springframework.stereotype.Service

@Service
class AuditService(private val auditRepository: AuditRepository) {

    fun logBookingCreation(bookingEvent: BookingEvent): BookingEvent {
        bookingEvent.price?.let {
            auditRepository.saveBookingCreationEvent(bookingEvent.bookingId, bookingEvent.userId, bookingEvent.propertyId,
                it, bookingEvent.currency)
        }
        return bookingEvent
    }

    fun logBookingCancellation(bookingEvent: BookingEvent) {
        bookingEvent.refundAmount?.let {
            auditRepository.saveBookingCancellationEvent(bookingEvent.bookingId, bookingEvent.userId, bookingEvent.reason,
                it, bookingEvent.currency)
        }
    }

    fun logBookingStatusUpdate(bookingEvent: BookingEvent) {
        auditRepository.saveBookingStatusUpdate(bookingEvent.bookingId, bookingEvent.previousStatus, bookingEvent.newStatus)
    }
}