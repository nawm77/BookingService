package com.rus.nawm.mainservice.repository;

import com.rus.nawm.mainservice.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}