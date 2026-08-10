    package com.commercehub.inventory_service.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

    @MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
            LocalDateTime now = LocalDateTime.now();
            createdAt = now;
            updatedAt = now;
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = LocalDateTime.now();
        }

    }