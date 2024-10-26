package com.rus.nawm.mainservice.repository;

import com.rus.nawm.mainservice.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
  @Query(value = "select * from document where owner_id = ?1", nativeQuery = true)
  Optional<Document> getDocumentsByUserId(String userId);
}
