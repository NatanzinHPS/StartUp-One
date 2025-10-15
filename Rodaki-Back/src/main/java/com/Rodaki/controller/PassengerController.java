package com.Rodaki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Rodaki.dto.PassengerDTO;
import com.Rodaki.entity.Passenger;
import com.Rodaki.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "http://localhost:4200")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> salvar(@RequestBody Passenger passenger) {
        if (passenger.getUser() == null || passenger.getUser().getId() == null) {
            throw new RuntimeException("User ID é obrigatório");
        }
        
        Passenger saved = passengerService.salvar(passenger);
        return ResponseEntity.ok(new PassengerDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> listarTodos() {
        List<PassengerDTO> dtos = passengerService.listarTodos()
            .stream()
            .map(PassengerDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> buscarPorId(@PathVariable Long id) {
        return passengerService.buscarPorId(id)
            .map(passenger -> ResponseEntity.ok(new PassengerDTO(passenger)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody Passenger passenger) {
        passenger.setId(id);
        Passenger updated = passengerService.salvar(passenger);
        return ResponseEntity.ok(new PassengerDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        passengerService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}