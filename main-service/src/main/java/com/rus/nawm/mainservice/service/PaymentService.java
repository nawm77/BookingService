package com.rus.nawm.mainservice.service;

import com.rus.nawm.mainservice.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(String id);
    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    void deletePayment(String id);
}