package com.rus.nawm.mainservice.repository;

import com.rus.nawm.mainservice.domain.Booking;
import com.rus.nawm.mainservice.domain.Property;
import com.rus.nawm.mainservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
  List<Booking> findByUserId(User user);
  List<Booking> findByPropertyId(Property property);
  List<Booking> findByStatus(String status);
}