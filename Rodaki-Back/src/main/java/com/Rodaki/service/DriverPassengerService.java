package com.Rodaki.service;

import com.Rodaki.entity.Driver;
import com.Rodaki.entity.Passenger;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class DriverPassengerService {

    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    public DriverPassengerService(DriverRepository driverRepository,
                                 PassengerRepository passengerRepository) {
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public void assignPassengerToDriver(Long driverId, Long passengerId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
        
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        driver.addPassenger(passenger);
        driverRepository.save(driver);
    }

    @Transactional
    public void removePassengerFromDriver(Long driverId, Long passengerId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
        
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        driver.removePassenger(passenger);
        driverRepository.save(driver);
    }

    public Set<Passenger> getPassengersByDriver(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));
        return driver.getPassengers();
    }

    public Set<Driver> getDriversByPassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));
        return passenger.getDrivers();
    }

    @Transactional
    public void assignMultiplePassengersToDriver(Long driverId, List<Long> passengerIds) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

        for (Long passengerId : passengerIds) {
            Passenger passenger = passengerRepository.findById(passengerId)
                    .orElseThrow(() -> new RuntimeException("Passageiro " + passengerId + " não encontrado"));
            driver.addPassenger(passenger);
        }

        driverRepository.save(driver);
    }
}