package com.rus.nawm.auditlogservice.repository

import com.rus.nawm.auditlogservice.event.BookingEvent
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
open class AuditRepository(private val jdbcTemplate: JdbcTemplate) {
    @Transactional
    open fun saveBookingCreationEvent(
        bookingEvent: BookingEvent
    ) {
        val sql =
            "INSERT INTO audit_log (event_type, booking_id, user_id, property_id, price, currency, previous_status, new_status, reason, refund_amount, event_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())"
        jdbcTemplate.update(sql, "CREATE_BOOKING",
            bookingEvent.bookingId,
            bookingEvent.userId,
            bookingEvent.propertyId,
            bookingEvent.price,
            bookingEvent.currency,
            bookingEvent.previousStatus,
            bookingEvent.newStatus,
            bookingEvent.reason,
            bookingEvent.refundAmount)
    }

    @Transactional
    open fun saveBookingCancellationEvent(
        bookingEvent: BookingEvent
    ) {
        val sql =
            "INSERT INTO audit_log (event_type, booking_id, user_id, property_id, price, currency, previous_status, new_status, reason, refund_amount, event_timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())"
        jdbcTemplate.update(sql, "CANCEL_BOOKING",
            bookingEvent.bookingId,
            bookingEvent.userId,
            bookingEvent.propertyId,
            bookingEvent.price,
            bookingEvent.currency,
            bookingEvent.previousStatus,
            bookingEvent.newStatus,
            bookingEvent.reason,
            bookingEvent.refundAmount)
    }

    @Transactional
    open fun saveBookingStatusUpdate(bookingEvent: BookingEvent) {
        val sql = "INSERT INTO audit_log (event_type, booking_id, user_id, property_id, price, currency, previous_status, new_status, reason, refund_amount, event_timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())"
        jdbcTemplate.update(sql, "UPDATE_BOOKING_STATUS",
            bookingEvent.bookingId,
            bookingEvent.userId,
            bookingEvent.propertyId,
            bookingEvent.price,
            bookingEvent.currency,
            bookingEvent.previousStatus,
            bookingEvent.newStatus,
            bookingEvent.reason,
            bookingEvent.refundAmount)
    }
}