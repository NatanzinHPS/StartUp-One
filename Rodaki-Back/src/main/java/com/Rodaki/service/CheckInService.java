package com.Rodaki.service;

import com.Rodaki.entity.CheckIn;
import com.Rodaki.entity.CheckIn.CheckInStatus;
import com.Rodaki.entity.Driver;
import com.Rodaki.entity.Passenger;
import com.Rodaki.repository.CheckInRepository;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public CheckInService(CheckInRepository checkInRepository,
                         PassengerRepository passengerRepository,
                         DriverRepository driverRepository) {
        this.checkInRepository = checkInRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
    }

    @Transactional
    public CheckIn create(Long passengerId, Long driverId, LocalDate date, CheckInStatus status) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));
        
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

        // Verifica se já existe check-in nessa data
        Optional<CheckIn> existing = checkInRepository
                .findByPassengerIdAndDriverIdAndDate(passengerId, driverId, date);
        
        if (existing.isPresent()) {
            throw new RuntimeException("Já existe check-in para esta data");
        }

        CheckIn checkIn = new CheckIn(passenger, driver, date, status);
        return checkInRepository.save(checkIn);
    }

    public List<CheckIn> findByPassengerId(Long passengerId) {
        return checkInRepository.findByPassengerId(passengerId);
    }

    public List<CheckIn> findByDriverId(Long driverId) {
        return checkInRepository.findByDriverId(driverId);
    }

    public List<CheckIn> findByDate(LocalDate date) {
        return checkInRepository.findByDate(date);
    }

    public List<CheckIn> findByDriverAndDate(Long driverId, LocalDate date) {
        return checkInRepository.findByDriverIdAndDate(driverId, date);
    }

    @Transactional
    public CheckIn updateStatus(Long checkInId, CheckInStatus status) {
        CheckIn checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new RuntimeException("Check-in não encontrado"));
        
        checkIn.setStatus(status);
        return checkInRepository.save(checkIn);
    }

    @Transactional
    public void delete(Long id) {
        if (!checkInRepository.existsById(id)) {
            throw new RuntimeException("Check-in não encontrado");
        }
        checkInRepository.deleteById(id);
    }
}