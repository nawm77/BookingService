package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
