package com.coop.demo.coopVerboh.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "intent_pattern")
@Data
public class IntentPattern {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "intent_code", referencedColumnName = "code")
    private Intent intent;

    @Column(nullable = false)
    private String pattern;

    private int priority = 1;
    private boolean enabled = true;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
