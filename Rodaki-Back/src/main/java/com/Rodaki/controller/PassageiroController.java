package com.Rodaki.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Rodaki.dto.PassageiroDTO;
import com.Rodaki.entity.Passageiro;
import com.Rodaki.service.PassageiroService;

@RestController
@RequestMapping("/api/passageiros")
@CrossOrigin(origins = "http://localhost:4200")
public class PassageiroController {
    private final PassageiroService passageiroService;

    public PassageiroController(PassageiroService passageiroService) {
        this.passageiroService = passageiroService;
    }

    @PostMapping
    public ResponseEntity<PassageiroDTO> salvar(@RequestBody Passageiro passageiro) {
        if (passageiro.getUser() == null || passageiro.getUser().getId() == null) {
            throw new RuntimeException("User ID é obrigatório");
        }
        
        Passageiro saved = passageiroService.salvar(passageiro);
        return ResponseEntity.ok(new PassageiroDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<PassageiroDTO>> listarTodos() {
        List<PassageiroDTO> dtos = passageiroService.listarTodos()
            .stream()
            .map(PassageiroDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassageiroDTO> buscarPorId(@PathVariable Long id) {
        return passageiroService.buscarPorId(id)
            .map(passageiro -> ResponseEntity.ok(new PassageiroDTO(passageiro)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassageiroDTO> atualizar(
            @PathVariable Long id, 
            @RequestBody Passageiro passageiro) {
        passageiro.setId(id);
        Passageiro updated = passageiroService.salvar(passageiro);
        return ResponseEntity.ok(new PassageiroDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        passageiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}