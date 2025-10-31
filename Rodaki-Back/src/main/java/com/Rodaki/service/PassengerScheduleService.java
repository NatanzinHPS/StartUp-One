package com.Rodaki.service;

import com.Rodaki.entity.Passenger;
import com.Rodaki.entity.PassengerSchedule;
import com.Rodaki.entity.PassengerSchedule.Period;
import com.Rodaki.entity.PassengerSchedule.TripType;
import com.Rodaki.repository.PassengerRepository;
import com.Rodaki.repository.PassengerScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PassengerScheduleService {

    private final PassengerScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;

    public PassengerScheduleService(PassengerScheduleRepository scheduleRepository,
                                   PassengerRepository passengerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public PassengerSchedule create(Long passengerId, Integer dayOfWeek,
                                   Period period, TripType tripType) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        PassengerSchedule newSchedule = new PassengerSchedule(passenger, dayOfWeek, period, tripType);
        return scheduleRepository.save(newSchedule);
    }

    public List<PassengerSchedule> findByPassengerId(Long passengerId) {
        return scheduleRepository.findByPassengerId(passengerId);
    }

    public List<PassengerSchedule> findActiveByPassengerId(Long passengerId) {
        return scheduleRepository.findByPassengerIdAndIsActiveTrue(passengerId);
    }

    public List<PassengerSchedule> findByPassengerAndDay(Long passengerId, Integer dayOfWeek) {
        return scheduleRepository.findByPassengerIdAndDayOfWeek(passengerId, dayOfWeek);
    }


    @Transactional
    public PassengerSchedule update(Long id, Integer dayOfWeek,
                                   Period period, TripType tripType, Boolean isActive) {
        PassengerSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (dayOfWeek != null) existing.setDayOfWeek(dayOfWeek);
        if (period != null) existing.setPeriod(period);
        if (tripType != null) existing.setTripType(tripType);
        if (isActive != null) existing.setIsActive(isActive);

        return scheduleRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public PassengerSchedule toggleActive(Long id) {
        PassengerSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setIsActive(!schedule.getIsActive());
        return scheduleRepository.save(schedule);
    }
}