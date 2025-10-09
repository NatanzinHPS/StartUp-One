package com.Rodaki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "passageiros")
public class Passageiro {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime createdAt;

    public Passageiro() {
        this.createdAt = LocalDateTime.now();
    }

    public Passageiro(User user, Double latitude, Double longitude) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}