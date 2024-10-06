package com.rus.nawm.mainservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{0,2}?[-. ]?\\(?\\d{1,4}?\\)?[-. ]?\\d{1,4}[-. ]?\\d{1,4}[-. ]?\\d{1,9}$", message = "Phone number should be valid")
    @NotBlank(message = "Phone number is required")
    @Column(unique = true)
    private String phoneNumber;
}