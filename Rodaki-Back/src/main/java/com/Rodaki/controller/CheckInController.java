package com.Rodaki.controller;

import com.Rodaki.dto.CheckInDTO;
import com.Rodaki.entity.CheckIn.CheckInStatus;
import com.Rodaki.service.CheckInService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checkins")
@CrossOrigin(origins = "http://localhost:4200")
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping
    public ResponseEntity<CheckInDTO> create(@RequestBody Map<String, Object> request) {
        Long passengerId = Long.valueOf(request.get("passengerId").toString());
        Long driverId = Long.valueOf(request.get("driverId").toString());
        LocalDate date = LocalDate.parse(request.get("date").toString());
        CheckInStatus status = CheckInStatus.valueOf(request.get("status").toString());

        var checkIn = checkInService.create(passengerId, driverId, date, status);
        return ResponseEntity.ok(new CheckInDTO(checkIn));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<CheckInDTO>> findByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(checkInService.findByPassengerId(passengerId)
                .stream().map(CheckInDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<CheckInDTO>> findByDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(checkInService.findByDriverId(driverId)
                .stream().map(CheckInDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<CheckInDTO>> findByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(checkInService.findByDate(date)
                .stream().map(CheckInDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/driver/{driverId}/date/{date}")
    public ResponseEntity<List<CheckInDTO>> findByDriverAndDate(
            @PathVariable Long driverId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(checkInService.findByDriverAndDate(driverId, date)
                .stream().map(CheckInDTO::new).collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CheckInDTO> updateStatus(@PathVariable Long id,
                                                   @RequestBody Map<String, String> request) {
        CheckInStatus status = CheckInStatus.valueOf(request.get("status"));
        var updated = checkInService.updateStatus(id, status);
        return ResponseEntity.ok(new CheckInDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        checkInService.delete(id);
        return ResponseEntity.noContent().build();
    }
}