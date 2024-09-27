package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Property property;
  private LocalDateTime bookingDate;
  private LocalDate startDate;
  private LocalDate endDate;
  private double totalPrice;
  private String currency; //EUR, USD, RUB
  private String status; // "PENDING", "CONFIRMED", "CANCELLED"
}
