package com.Rodaki.dto;

import com.Rodaki.entity.PassengerSchedule;

public class PassengerScheduleDTO {
    private Long id;
    private Long passengerId;
    private Integer dayOfWeek;
    private String period;
    private String tripType;
    private Boolean isActive;

    public PassengerScheduleDTO() {}

    public PassengerScheduleDTO(PassengerSchedule schedule) {
        this.id = schedule.getId();
        this.passengerId = schedule.getPassenger().getId();
        this.dayOfWeek = schedule.getDayOfWeek();
        this.period = schedule.getPeriod().name();
        this.tripType = schedule.getTripType().name();
        this.isActive = schedule.getIsActive();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }
    public Integer getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getTripType() { return tripType; }
    public void setTripType(String tripType) { this.tripType = tripType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}