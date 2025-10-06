package com.Rodaki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Rodaki.dto.MotoristaDTO;
import com.Rodaki.entity.Motorista;
import com.Rodaki.service.MotoristaService;

@RestController
@RequestMapping("/api/motoristas")
@CrossOrigin(origins = "http://localhost:4200")
public class MotoristaController {
    private final MotoristaService motoristaService;

    public MotoristaController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @PostMapping
    public ResponseEntity<MotoristaDTO> salvar(@RequestBody Motorista motorista) {
        if (motorista.getUser() == null || motorista.getUser().getId() == null) {
            throw new RuntimeException("User ID é obrigatório");
        }
        
        Motorista saved = motoristaService.salvar(motorista);
        return ResponseEntity.ok(new MotoristaDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<MotoristaDTO>> listarTodos() {
        List<MotoristaDTO> dtos = motoristaService.listarTodos()
            .stream()
            .map(MotoristaDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> buscarPorId(@PathVariable Long id) {
        return motoristaService.buscarPorId(id)
            .map(motorista -> ResponseEntity.ok(new MotoristaDTO(motorista)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoristaDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody Motorista motorista) {
        motorista.setId(id);
        Motorista updated = motoristaService.salvar(motorista);
        return ResponseEntity.ok(new MotoristaDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoristaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}