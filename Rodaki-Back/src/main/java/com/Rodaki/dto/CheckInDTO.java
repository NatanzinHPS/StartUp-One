package com.Rodaki.dto;

import com.Rodaki.entity.CheckIn;
import java.time.LocalDate;

public class CheckInDTO {
    private Long id;
    private Long passengerId;
    private String passengerName;
    private Long driverId;
    private String driverName;
    private LocalDate date;
    private String status;

    public CheckInDTO() {}

    public CheckInDTO(CheckIn checkIn) {
        this.id = checkIn.getId();
        this.passengerId = checkIn.getPassenger().getId();
        this.passengerName = checkIn.getPassenger().getUser().getName();
        this.driverId = checkIn.getDriver().getId();
        this.driverName = checkIn.getDriver().getUser().getName();
        this.date = checkIn.getDate();
        this.status = checkIn.getStatus().name();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}