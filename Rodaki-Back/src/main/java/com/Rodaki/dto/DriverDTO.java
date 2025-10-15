package com.Rodaki.dto;

import com.Rodaki.entity.Driver;
import java.util.Set;
import java.util.stream.Collectors;

public class DriverDTO {
    private Long id;
    private UserDTO user;
    private Set<Long> passengerIds;

    public DriverDTO() {}

    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.user = new UserDTO(driver.getUser());
        this.passengerIds = driver.getPassengers()
            .stream()
            .map(p -> p.getId())
            .collect(Collectors.toSet());
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }

    public Set<Long> getPassengerIds() { return passengerIds; }
    public void setPassengerIds(Set<Long> passengerIds) { this.passengerIds = passengerIds; }
}