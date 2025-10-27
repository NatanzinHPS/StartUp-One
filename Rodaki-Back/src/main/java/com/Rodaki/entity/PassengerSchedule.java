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
    private Integer dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "period", nullable = false)
    private Period period;

    @Enumerated(EnumType.STRING)
    @Column(name = "trip_type", nullable = false)
    private TripType tripType;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public enum Period {
        MORNING,
        AFTERNOON,
        NIGHT
    }

    public enum TripType {
        ONE_WAY,
        RETURN,
        ROUND_TRIP
    }

    public PassengerSchedule() {}

    public PassengerSchedule(Passenger passenger, Integer dayOfWeek, Period period, TripType tripType) {
        this.passenger = passenger;
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.tripType = tripType;
        this.isActive = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Period getPeriod() { return period; }
    public void setPeriod(Period period) { this.period = period; }

    public TripType getTripType() { return tripType; }
    public void setTripType(TripType tripType) { this.tripType = tripType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

}