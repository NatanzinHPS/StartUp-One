package com.Rodaki.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "passenger_schedules")
public class PassengerSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek; // 1=Monday, 7=Sunday

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Schedule schedule;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public PassengerSchedule() {}

    public PassengerSchedule(Passenger passenger, Integer dayOfWeek, Schedule schedule) {
        this.passenger = passenger;
        this.dayOfWeek = dayOfWeek;
        this.schedule = schedule;
        this.isActive = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public enum Schedule {
        MORNING,
        AFTERNOON,
        BOTH
    }
}