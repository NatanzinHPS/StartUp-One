package com.Rodaki.dto;

public class RoutePointDTO {
    private Long passengerId;
    private String passengerName;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer order;
    private String schedule;
    private String phone;

    public RoutePointDTO() {}

    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }

    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}