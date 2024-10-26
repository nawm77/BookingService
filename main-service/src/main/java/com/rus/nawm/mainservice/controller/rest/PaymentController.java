package com.rus.nawm.mainservice.controller.rest;

import com.rus.nawm.mainservice.domain.Payment;
import com.rus.nawm.mainservice.service.PaymentService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Payment>>> getAllPayments() {
        List<EntityModel<Payment>> payments = paymentService.getAllPayments().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Payment>> getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(payment -> ResponseEntity.ok(toModel(payment)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Payment>> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        EntityModel<Payment> paymentModel = toModel(createdPayment);

        return ResponseEntity
                .created(paymentModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(paymentModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Payment>> updatePayment(@PathVariable String id, @RequestBody Payment payment) {
        return paymentService.getPaymentById(id)
                .map(existingPayment -> {
                    payment.setId(existingPayment.getId());
                    Payment updatedPayment = paymentService.updatePayment(payment);
                    return ResponseEntity.ok(toModel(updatedPayment));
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(payment -> {
                    paymentService.deletePayment(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private EntityModel<Payment> toModel(Payment payment) {
        EntityModel<Payment> paymentModel = EntityModel.of(payment);

        Link selfLink = linkTo(methodOn(PaymentController.class).getPaymentById(payment.getId())).withSelfRel();
        Link allPaymentsLink = linkTo(methodOn(PaymentController.class).getAllPayments()).withRel("payments");
        paymentModel.add(selfLink);
        paymentModel.add(allPaymentsLink);

        return paymentModel;
    }
}