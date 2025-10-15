package com.Rodaki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "passengers")
public class Passenger {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "passengers")
    private Set<Driver> drivers = new HashSet<>();

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PassengerSchedule> schedules = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Passenger() {}

    public Passenger(User user) {
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Set<Driver> getDrivers() { return drivers; }
    public void setDrivers(Set<Driver> drivers) { this.drivers = drivers; }

    public Set<Address> getAddresses() { return addresses; }
    public void setAddresses(Set<Address> addresses) { this.addresses = addresses; }

    public Set<PassengerSchedule> getSchedules() { return schedules; }
    public void setSchedules(Set<PassengerSchedule> schedules) { this.schedules = schedules; }

    // Helper methods
    public void addAddress(Address address) {
        addresses.add(address);
        address.setPassenger(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setPassenger(null);
    }

    public void addSchedule(PassengerSchedule schedule) {
        schedules.add(schedule);
        schedule.setPassenger(this);
    }

    public void removeSchedule(PassengerSchedule schedule) {
        schedules.remove(schedule);
        schedule.setPassenger(null);
    }
}