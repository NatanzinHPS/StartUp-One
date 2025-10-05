package com.Rodaki.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class ViaCepService {

    private static final String VIACEP_URL = "https://viacep.com.br/ws";
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ViaCepService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, Object> buscarEnderecoPorCep(String cep) {
        try {
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            
            if (cepLimpo.length() != 8) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("error", "CEP inválido");
                return errorResult;
            }

            String url = String.format("%s/%s/json/", VIACEP_URL, cepLimpo);
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Map<String, Object> result = new HashMap<>();

            if (root.has("erro") && root.get("erro").asBoolean()) {
                result.put("success", false);
                result.put("error", "CEP não encontrado");
            } else {
                result.put("success", true);
                result.put("cep", root.path("cep").asText());
                result.put("logradouro", root.path("logradouro").asText());
                result.put("complemento", root.path("complemento").asText());
                result.put("bairro", root.path("bairro").asText());
                result.put("localidade", root.path("localidade").asText()); // cidade
                result.put("uf", root.path("uf").asText()); // estado
                result.put("ibge", root.path("ibge").asText());
                result.put("gia", root.path("gia").asText());
                result.put("ddd", root.path("ddd").asText());
                result.put("siafi", root.path("siafi").asText());
            }

            return result;
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "Erro ao buscar CEP: " + e.getMessage());
            return errorResult;
        }
    }

    public boolean validarCep(String cep) {
        if (cep == null) return false;
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        return cepLimpo.length() == 8;
    }


    public String formatarCep(String cep) {
        if (cep == null) return "";
        String cepLimpo = cep.replaceAll("[^0-9]", "");
        if (cepLimpo.length() != 8) return cep;
        return cepLimpo.substring(0, 5) + "-" + cepLimpo.substring(5);
    }
}