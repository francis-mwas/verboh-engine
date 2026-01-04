package com.coop.demo.coopVerboh.repository;

import com.coop.demo.coopVerboh.model.Intent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IntentRepository extends JpaRepository<Intent, UUID> {
    Optional<Intent> findByCodeAndEnabledTrue(String code);
}
