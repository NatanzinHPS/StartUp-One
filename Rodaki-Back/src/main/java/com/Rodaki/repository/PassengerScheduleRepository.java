package com.Rodaki.repository;

import com.Rodaki.entity.PassengerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PassengerScheduleRepository extends JpaRepository<PassengerSchedule, Long> {
    List<PassengerSchedule> findByPassengerId(Long passengerId);
    List<PassengerSchedule> findByPassengerIdAndIsActiveTrue(Long passengerId);
    List<PassengerSchedule> findByPassengerIdAndDayOfWeek(Long passengerId, Integer dayOfWeek);
}