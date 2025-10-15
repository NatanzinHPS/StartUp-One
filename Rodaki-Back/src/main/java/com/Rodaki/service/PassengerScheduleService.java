package com.Rodaki.service;

import com.Rodaki.entity.Passenger;
import com.Rodaki.entity.PassengerSchedule;
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
                                   PassengerSchedule.Schedule schedule) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        PassengerSchedule newSchedule = new PassengerSchedule(passenger, dayOfWeek, schedule);
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
                                   PassengerSchedule.Schedule schedule, Boolean isActive) {
        PassengerSchedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (dayOfWeek != null) existing.setDayOfWeek(dayOfWeek);
        if (schedule != null) existing.setSchedule(schedule);
        if (isActive != null) existing.setIsActive(isActive);

        return scheduleRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        scheduleRepository.deleteById(id);
    }

    @Transactional
    public PassengerSchedule toggleActive(Long id) {
        PassengerSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        
        schedule.setIsActive(!schedule.getIsActive());
        return scheduleRepository.save(schedule);
    }
}