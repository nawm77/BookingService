package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    private Booking booking;
    private double amount;
    private String currency;
    private LocalDateTime paymentDate;
    private String status; // "PAID", "FAILED", "REFUNDED"
}