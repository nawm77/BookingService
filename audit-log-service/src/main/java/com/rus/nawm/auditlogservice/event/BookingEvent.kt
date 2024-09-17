package com.rus.nawm.auditlogservice.event

import java.io.Serializable

data class BookingEvent(
    val eventType: String? = null,
    val bookingId: String? = null,
    val userId: String? = null,
    val propertyId: String? = null,
    val price: Double? = null,
    val currency: String? = null,
    val previousStatus: String? = null,
    val newStatus: String? = null,
    val reason: String? = null,
    val refundAmount: Double? = null
) : Serializable