package com.Rodaki.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Rodaki.service.ViaCepService;
import com.Rodaki.service.GoogleMapsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cep")
@CrossOrigin(origins = "*")
public class CepController {

    private final ViaCepService viaCepService;
    private final GoogleMapsService googleMapsService;

    public CepController(ViaCepService viaCepService, GoogleMapsService googleMapsService) {
        this.viaCepService = viaCepService;
        this.googleMapsService = googleMapsService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Map<String, Object>> buscarCep(@PathVariable String cep) {
        Map<String, Object> result = new HashMap<>();

        Map<String, Object> viaCepResult = viaCepService.buscarEnderecoPorCep(cep);
        
        if (Boolean.FALSE.equals(viaCepResult.get("success"))) {
            return ResponseEntity.badRequest().body(viaCepResult);
        }

        String enderecoCompleto = String.format("%s, %s, %s - %s, Brasil",
                viaCepResult.get("logradouro"),
                viaCepResult.get("bairro"),
                viaCepResult.get("localidade"),
                viaCepResult.get("uf"));

        Map<String, Object> geocodeResult = googleMapsService.geocodeAddress(enderecoCompleto);

        result.put("success", true);
        result.put("cep", viaCepResult.get("cep"));
        result.put("rua", viaCepResult.get("logradouro"));
        result.put("bairro", viaCepResult.get("bairro"));
        result.put("cidade", viaCepResult.get("localidade"));
        result.put("estado", viaCepResult.get("uf"));
        result.put("complementoViaCep", viaCepResult.get("complemento"));

        if (Boolean.TRUE.equals(geocodeResult.get("success"))) {
            result.put("latitude", geocodeResult.get("latitude"));
            result.put("longitude", geocodeResult.get("longitude"));
            result.put("enderecoFormatado", geocodeResult.get("formattedAddress"));
        } else {
            result.put("latitude", null);
            result.put("longitude", null);
            result.put("geocodeError", "Coordenadas não encontradas");
        }

        return ResponseEntity.ok(result);
    }

    /*
     Valida formato do CEP
     GET /api/cep/validar?cep=12345678
    */
    @GetMapping("/validar")
    public ResponseEntity<Map<String, Object>> validarCep(@RequestParam String cep) {
        Map<String, Object> result = new HashMap<>();
        boolean valido = viaCepService.validarCep(cep);
        
        result.put("valido", valido);
        if (valido) {
            result.put("cepFormatado", viaCepService.formatarCep(cep));
        }
        
        return ResponseEntity.ok(result);
    }
}