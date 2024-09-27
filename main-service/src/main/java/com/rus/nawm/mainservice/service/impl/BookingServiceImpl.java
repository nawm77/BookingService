package com.rus.nawm.mainservice.service.impl;

import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.domain.User;
import com.rus.nawm.mainservice.repository.BookingRepository;
import com.rus.nawm.mainservice.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
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
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(String id) {
        bookingRepository.deleteById(id);
    }
}