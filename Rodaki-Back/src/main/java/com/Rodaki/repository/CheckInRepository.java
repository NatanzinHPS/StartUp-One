package com.Rodaki.repository;

import com.Rodaki.entity.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findByPassengerId(Long passengerId);
    List<CheckIn> findByDriverId(Long driverId);
    List<CheckIn> findByDate(LocalDate date);
    List<CheckIn> findByDriverIdAndDate(Long driverId, LocalDate date);
    Optional<CheckIn> findByPassengerIdAndDriverIdAndDate(Long passengerId, Long driverId, LocalDate date);
}