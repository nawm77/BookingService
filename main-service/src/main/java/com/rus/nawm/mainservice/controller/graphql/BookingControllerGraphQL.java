package com.rus.nawm.mainservice.controller.graphql;

import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingControllerGraphQL {
  private final BookingService bookingService;

  @MutationMapping
  public Booking createBooking(@Argument("input") @Valid Booking booking) {
    return bookingService.createBooking(booking);
  }

  @MutationMapping
  public Booking updateBooking(@Argument("input") @Valid Booking booking) {
    return bookingService.updateBooking(booking);
  }

  @QueryMapping
  public List<Booking> getBookings() {
    return bookingService.getAllBookings();
  }

  @QueryMapping
  public Booking getBooking(@Argument("id") String id) {
    return bookingService.getBookingById(id).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
  }
}