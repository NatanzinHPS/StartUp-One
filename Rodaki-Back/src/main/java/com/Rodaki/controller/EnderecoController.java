package com.Rodaki.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Rodaki.dto.EnderecoDTO;
import com.Rodaki.entity.Endereco;
import com.Rodaki.service.EnderecoService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping("/passageiro/{passageiroId}")
    public ResponseEntity<EnderecoDTO> criar(
            @PathVariable Long passageiroId,
            @RequestBody Endereco endereco) {
        try {
            Endereco saved = enderecoService.salvar(endereco, passageiroId);
            return ResponseEntity.status(HttpStatus.CREATED).body(new EnderecoDTO(saved));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/passageiro/{passageiroId}")
    public ResponseEntity<List<EnderecoDTO>> listarPorPassageiro(@PathVariable Long passageiroId) {
        List<EnderecoDTO> enderecos = enderecoService.listarPorPassageiro(passageiroId)
                .stream()
                .map(EnderecoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> buscarPorId(@PathVariable Long id) {
        try {
            Endereco endereco = enderecoService.buscarPorId(id);
            return ResponseEntity.ok(new EnderecoDTO(endereco));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> atualizar(
            @PathVariable Long id,
            @RequestBody Endereco endereco) {
        try {
            Endereco updated = enderecoService.atualizar(id, endereco);
            return ResponseEntity.ok(new EnderecoDTO(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            enderecoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/principal")
    public ResponseEntity<EnderecoDTO> marcarComoPrincipal(@PathVariable Long id) {
        try {
            Endereco endereco = enderecoService.marcarComoPrincipal(id);
            return ResponseEntity.ok(new EnderecoDTO(endereco));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints para Google Maps API
    
    @GetMapping("/geocode")
    public ResponseEntity<Map<String, Object>> geocode(@RequestParam String endereco) {
        Map<String, Object> result = enderecoService.buscarCoordenadas(endereco);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<Map<String, Object>> buscarPorCep(@PathVariable String cep) {
        Map<String, Object> result = enderecoService.buscarPorCep(cep);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<Map<String, Object>> autocomplete(@RequestParam String input) {
        Map<String, Object> result = enderecoService.autocomplete(input);
        return ResponseEntity.ok(result);
    }
}
