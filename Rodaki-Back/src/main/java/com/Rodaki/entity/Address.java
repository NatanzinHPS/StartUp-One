package com.Rodaki.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    private String street;

    @Column(name = "number_street")
    private String numberStreet;

    private String city;

    private String state;

    private String cep;

    private Double latitude;

    private Double longitude;

    public Address() {}

    public Address(Passenger passenger, String street, String numberStreet, 
                   String city, String state, String cep, Double latitude, Double longitude) {
        this.passenger = passenger;
        this.street = street;
        this.numberStreet = numberStreet;
        this.city = city;
        this.state = state;
        this.cep = cep;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Passenger getPassenger() { return passenger; }
    public void setPassenger(Passenger passenger) { this.passenger = passenger; }

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

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (street != null) sb.append(street);
        if (numberStreet != null) sb.append(", ").append(numberStreet);
        if (city != null) sb.append(", ").append(city);
        if (state != null) sb.append(" - ").append(state);
        if (cep != null) sb.append(", CEP: ").append(cep);
        return sb.toString();
    }
}