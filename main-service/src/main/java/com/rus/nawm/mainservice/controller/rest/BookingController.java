package com.rus.nawm.mainservice.controller.rest;

import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.service.impl.BookingServiceImpl;
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
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingServiceImpl bookingServiceImpl;

    public BookingController(BookingServiceImpl bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<EntityModel<Booking>>> getAllBookings() {
        List<EntityModel<Booking>> bookings = bookingServiceImpl.getAllBookings().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Booking>> getBookingById(@PathVariable String id) {
        return bookingServiceImpl.getBookingById(id)
                .map(booking -> ResponseEntity.ok(toModel(booking)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Booking>> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingServiceImpl.createBooking(booking);
        EntityModel<Booking> bookingModel = toModel(createdBooking);
//todo сделать логику, что при оплате статус бронирования меняется на другой (CONFIRMED)
        return ResponseEntity
                .created(bookingModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(bookingModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Booking>> updateBooking(@PathVariable String id, @RequestBody Booking booking) {
        return bookingServiceImpl.getBookingById(id)
                .map(existingBooking -> {
                    booking.setId(existingBooking.getId());
                    Booking updatedBooking = bookingServiceImpl.updateBooking(booking);
                    return ResponseEntity.ok(toModel(updatedBooking));
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        return bookingServiceImpl.getBookingById(id)
                .map(booking -> {
                    bookingServiceImpl.deleteBooking(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private EntityModel<Booking> toModel(Booking booking) {
        EntityModel<Booking> bookingModel = EntityModel.of(booking);

        Link selfLink = linkTo(methodOn(BookingController.class).getBookingById(booking.getId())).withSelfRel();
        Link allBookingsLink = linkTo(methodOn(BookingController.class).getAllBookings()).withRel("bookings");

        bookingModel.add(selfLink);
        bookingModel.add(allBookingsLink);

        return bookingModel;
    }
}