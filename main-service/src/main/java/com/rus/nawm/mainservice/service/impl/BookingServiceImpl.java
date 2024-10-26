package com.rus.nawm.mainservice.service.impl;

import com.rus.nawm.auditlogservice.domain.BookingEvent;
import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.repository.BookingRepository;
import com.rus.nawm.mainservice.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
  @Value("${audit_booking.queue.name}")
  private String queueName;

  private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

  private final BookingRepository bookingRepository;
  private final RabbitTemplate rabbitTemplate;

  public BookingServiceImpl(BookingRepository bookingRepository, RabbitTemplate rabbitTemplate) {
    this.bookingRepository = bookingRepository;
    this.rabbitTemplate = rabbitTemplate;
  }

  public List<Booking> getAllBookings() {
    return bookingRepository.findAll();
  }

  public Optional<Booking> getBookingById(String id) {
    return bookingRepository.findById(id);
  }

  public List<Booking> getBookingsByUserId(User user) {
    return bookingRepository.findByUserId(user);
  }

  public List<Booking> getBookingsByPropertyId(Property property) {
    return bookingRepository.findByPropertyId(property);
  }

  public List<Booking> getBookingsByStatus(String status) {
    return bookingRepository.findByStatus(status);
  }

  public Booking createBooking(Booking booking) {
    booking = bookingRepository.save(booking);
    BookingEvent event = new BookingEvent(
            "CREATE_BOOKING",
            booking.getId(),
            booking.getUser().getId(),
            booking.getProperty().getId(),
            booking.getTotalPrice(),
            booking.getCurrency(),
            booking.getStatus(),
            booking.getStatus(),
            "default",
            0
    );
    rabbitTemplate.convertAndSend(queueName, event);
    return booking;
  }

  public Booking updateBooking(Booking booking) {
    return bookingRepository.save(booking);
  }

  public void deleteBooking(String id) {
    bookingRepository.deleteById(id);
  }
}