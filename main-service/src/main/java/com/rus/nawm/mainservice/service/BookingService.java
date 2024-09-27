package com.rus.nawm.mainservice.service;

import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.domain.User;

import java.util.List;
import java.util.Optional;

public interface BookingService {
  List<Booking> getAllBookings();

  Optional<Booking> getBookingById(String id);

  List<Booking> getBookingsByUserId(User user);

  List<Booking> getBookingsByPropertyId(Property property);

  List<Booking> getBookingsByStatus(String status);

  Booking createBooking(Booking booking);

  Booking updateBooking(Booking booking);

  void deleteBooking(String id);
}
