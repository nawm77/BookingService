package com.rus.nawm.mainservice.repository;

import com.rus.nawm.mainservice.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
}