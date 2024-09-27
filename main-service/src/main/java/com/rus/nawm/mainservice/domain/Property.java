package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String address;
    @ManyToOne
    private User owner;
    private double pricePerNight;
    private String currency;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}