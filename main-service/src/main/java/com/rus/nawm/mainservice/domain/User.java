package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
}