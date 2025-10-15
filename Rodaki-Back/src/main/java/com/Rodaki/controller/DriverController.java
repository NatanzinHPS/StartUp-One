package com.Rodaki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Rodaki.dto.DriverDTO;
import com.Rodaki.entity.Driver;
import com.Rodaki.service.DriverService;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "http://localhost:4200")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverDTO> salvar(@RequestBody Driver driver) {
        if (driver.getUser() == null || driver.getUser().getId() == null) {
            throw new RuntimeException("User ID é obrigatório");
        }
        
        Driver saved = driverService.salvar(driver);
        return ResponseEntity.ok(new DriverDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<DriverDTO>> listarTodos() {
        List<DriverDTO> dtos = driverService.listarTodos()
            .stream()
            .map(DriverDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> buscarPorId(@PathVariable Long id) {
        return driverService.buscarPorId(id)
            .map(driver -> ResponseEntity.ok(new DriverDTO(driver)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody Driver driver) {
        driver.setId(id);
        Driver updated = driverService.salvar(driver);
        return ResponseEntity.ok(new DriverDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        driverService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}