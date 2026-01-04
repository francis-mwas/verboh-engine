package com.coop.demo.coopVerboh.repository;

import com.coop.demo.coopVerboh.model.IntentPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IntentPatternRepository extends JpaRepository<IntentPattern, UUID> {

    @Query("SELECT p FROM IntentPattern p WHERE p.enabled = true ORDER BY p.priority DESC")
    List<IntentPattern> findAllEnabled();
}
