package com.library.system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
