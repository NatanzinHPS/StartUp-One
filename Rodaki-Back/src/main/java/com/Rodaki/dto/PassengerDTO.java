package com.Rodaki.dto;

import com.Rodaki.entity.Passenger;
import java.util.Set;
import java.util.stream.Collectors;

public class PassengerDTO {
    private Long id;
    private UserDTO user;
    private Set<Long> driverIds;
    private Set<AddressDTO> addresses;

    public PassengerDTO() {}

    public PassengerDTO(Passenger passenger) {
        this.id = passenger.getId();
        this.user = new UserDTO(passenger.getUser());
        this.driverIds = passenger.getDrivers()
            .stream()
            .map(d -> d.getId())
            .collect(Collectors.toSet());
        this.addresses = passenger.getAddresses()
            .stream()
            .map(AddressDTO::new)
            .collect(Collectors.toSet());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public Set<Long> getDriverIds() { return driverIds; }
    public void setDriverIds(Set<Long> driverIds) { this.driverIds = driverIds; }

    public Set<AddressDTO> getAddresses() { return addresses; }
    public void setAddresses(Set<AddressDTO> addresses) { this.addresses = addresses; }
}