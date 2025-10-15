package com.Rodaki.controller;

import com.Rodaki.dto.DriverDTO;
import com.Rodaki.dto.PassengerDTO;
import com.Rodaki.service.DriverPassengerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/driver-passenger")
@CrossOrigin(origins = "http://localhost:4200")
public class DriverPassengerController {

    private final DriverPassengerService driverPassengerService;

    public DriverPassengerController(DriverPassengerService driverPassengerService) {
        this.driverPassengerService = driverPassengerService;
    }

    @PostMapping("/assign")
    public ResponseEntity<Map<String, String>> assignPassengerToDriver(
            @RequestBody Map<String, Long> request) {
        Long driverId = request.get("driverId");
        Long passengerId = request.get("passengerId");
        
        driverPassengerService.assignPassengerToDriver(driverId, passengerId);
        
        return ResponseEntity.ok(Map.of("message", "Passageiro atribuído com sucesso"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removePassengerFromDriver(
            @RequestBody Map<String, Long> request) {
        Long driverId = request.get("driverId");
        Long passengerId = request.get("passengerId");
        
        driverPassengerService.removePassengerFromDriver(driverId, passengerId);
        
        return ResponseEntity.ok(Map.of("message", "Passageiro removido com sucesso"));
    }

    @GetMapping("/driver/{driverId}/passengers")
    public ResponseEntity<List<PassengerDTO>> getPassengersByDriver(@PathVariable Long driverId) {
        var passengers = driverPassengerService.getPassengersByDriver(driverId)
                .stream()
                .map(PassengerDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/passenger/{passengerId}/drivers")
    public ResponseEntity<List<DriverDTO>> getDriversByPassenger(@PathVariable Long passengerId) {
        var drivers = driverPassengerService.getDriversByPassenger(passengerId)
                .stream()
                .map(DriverDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(drivers);
    }

    @PostMapping("/driver/{driverId}/assign-multiple")
    public ResponseEntity<Map<String, String>> assignMultiplePassengers(
            @PathVariable Long driverId,
            @RequestBody Map<String, List<Long>> request) {
        List<Long> passengerIds = request.get("passengerIds");
        
        driverPassengerService.assignMultiplePassengersToDriver(driverId, passengerIds);
        
        return ResponseEntity.ok(Map.of("message", "Passageiros atribuídos com sucesso"));
    }
}