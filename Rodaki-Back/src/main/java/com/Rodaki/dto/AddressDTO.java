package com.Rodaki.dto;

import com.Rodaki.entity.Address;

public class AddressDTO {
    private Long id;
    private Long passengerId;
    private String street;
    private String numberStreet;
    private String city;
    private String state;
    private String cep;
    private Double latitude;
    private Double longitude;
    private String fullAddress;

    public AddressDTO() {}

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.passengerId = address.getPassenger().getId();
        this.street = address.getStreet();
        this.numberStreet = address.getNumberStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.cep = address.getCep();
        this.latitude = address.getLatitude();
        this.longitude = address.getLongitude();
        this.fullAddress = address.getFullAddress();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getNumberStreet() { return numberStreet; }
    public void setNumberStreet(String numberStreet) { this.numberStreet = numberStreet; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getFullAddress() { return fullAddress; }
    public void setFullAddress(String fullAddress) { this.fullAddress = fullAddress; }
}