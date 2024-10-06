package com.rus.nawm.mainservice.controller.graphql;

import com.rus.nawm.mainservice.domain.Payment;
import com.rus.nawm.mainservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentControllerGraphQL {
  private final PaymentService paymentService;

  @MutationMapping
  public Payment createPayment(@Argument("input") @Valid Payment payment) {
    return paymentService.createPayment(payment);
  }

  @MutationMapping
  public Payment updatePayment(@Argument("input") @Valid Payment payment) {
    return paymentService.updatePayment(payment);
  }

  @QueryMapping
  public List<Payment> getPayments() {
    return paymentService.getAllPayments();
  }

  @QueryMapping
  public Payment getPayment(@Argument("id") String id) {
    return paymentService.getPaymentById(id).orElseThrow(() -> new IllegalArgumentException("Payment not found"));
  }
}