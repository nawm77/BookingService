package com.rus.nawm.auditlogservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuditRepository {

    private final JdbcTemplate jdbcTemplate;

    public AuditRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveBookingCreationEvent(String bookingId, String userId, String propertyId, double price, String currency) {
        String sql = "INSERT INTO audit_log (event_type, booking_id, user_id, property_id, price, currency, event_timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, "CREATE_BOOKING", bookingId, userId, propertyId, price, currency);
    }

    public void saveBookingCancellationEvent(String bookingId, String userId, String reason, double refundAmount, String currency) {
        String sql = "INSERT INTO audit_log (event_type, booking_id, user_id, reason, refund_amount, currency, event_timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, "CANCEL_BOOKING", bookingId, userId, reason, refundAmount, currency);
    }

    public void saveBookingStatusUpdate(String bookingId, String previousStatus, String newStatus) {
        String sql = "INSERT INTO audit_log (event_type, booking_id, previous_status, new_status, event_timestamp) " +
                "VALUES (?, ?, ?, ?, now())";
        jdbcTemplate.update(sql, "UPDATE_BOOKING_STATUS", bookingId, previousStatus, newStatus);
    }
}