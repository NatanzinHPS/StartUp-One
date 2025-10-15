package com.Rodaki.controller;

import com.Rodaki.dto.PassengerScheduleDTO;
import com.Rodaki.entity.PassengerSchedule;
import com.Rodaki.service.PassengerScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerScheduleController {

    private final PassengerScheduleService scheduleService;

    public PassengerScheduleController(PassengerScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<PassengerScheduleDTO> create(@RequestBody Map<String, Object> request) {
        Long passengerId = Long.valueOf(request.get("passengerId").toString());
        Integer dayOfWeek = Integer.valueOf(request.get("dayOfWeek").toString());
        PassengerSchedule.Schedule schedule = PassengerSchedule.Schedule.valueOf(
            request.get("schedule").toString()
        );

        var created = scheduleService.create(passengerId, dayOfWeek, schedule);
        return ResponseEntity.ok(new PassengerScheduleDTO(created));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<PassengerScheduleDTO>> findByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(scheduleService.findByPassengerId(passengerId)
                .stream().map(PassengerScheduleDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/passenger/{passengerId}/active")
    public ResponseEntity<List<PassengerScheduleDTO>> findActiveByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(scheduleService.findActiveByPassengerId(passengerId)
                .stream().map(PassengerScheduleDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/passenger/{passengerId}/day/{dayOfWeek}")
    public ResponseEntity<List<PassengerScheduleDTO>> findByPassengerAndDay(
            @PathVariable Long passengerId,
            @PathVariable Integer dayOfWeek) {
        return ResponseEntity.ok(scheduleService.findByPassengerAndDay(passengerId, dayOfWeek)
                .stream().map(PassengerScheduleDTO::new).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerScheduleDTO> update(@PathVariable Long id,
                                                       @RequestBody Map<String, Object> request) {
        Integer dayOfWeek = request.containsKey("dayOfWeek") 
            ? Integer.valueOf(request.get("dayOfWeek").toString()) : null;
        
        PassengerSchedule.Schedule schedule = request.containsKey("schedule")
            ? PassengerSchedule.Schedule.valueOf(request.get("schedule").toString()) : null;
        
        Boolean isActive = request.containsKey("isActive")
            ? Boolean.valueOf(request.get("isActive").toString()) : null;

        var updated = scheduleService.update(id, dayOfWeek, schedule, isActive);
        return ResponseEntity.ok(new PassengerScheduleDTO(updated));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<PassengerScheduleDTO> toggleActive(@PathVariable Long id) {
        var updated = scheduleService.toggleActive(id);
        return ResponseEntity.ok(new PassengerScheduleDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}