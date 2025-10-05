package com.Rodaki.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GoogleMapsService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Busca coordenadas (lat/lng) a partir de um endereço completo
     */
    public Map<String, Object> geocodeAddress(String endereco) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(GEOCODING_API_URL)
                    .queryParam("address", endereco)
                    .queryParam("key", apiKey)
                    .build()
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Map<String, Object> result = new HashMap<>();

            if ("OK".equals(root.path("status").asText())) {
                JsonNode location = root.path("results").get(0)
                        .path("geometry").path("location");
                
                result.put("success", true);
                result.put("latitude", location.path("lat").asDouble());
                result.put("longitude", location.path("lng").asDouble());
                result.put("formattedAddress", root.path("results").get(0)
                        .path("formatted_address").asText());
            } else {
                result.put("success", false);
                result.put("error", "Endereço não encontrado");
            }

            return result;
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "Erro ao buscar coordenadas: " + e.getMessage());
            return errorResult;
        }
    }

    public Map<String, Object> geocodeByCep(String cep) {
        String cepFormatted = cep.replaceAll("[^0-9]", "");
        return geocodeAddress(cepFormatted + ", Brasil");
    }

    public Map<String, Object> autocompleteAddress(String input) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(PLACES_API_URL)
                    .queryParam("input", input)
                    .queryParam("key", apiKey)
                    .queryParam("types", "address")
                    .queryParam("components", "country:br")
                    .build()
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Map<String, Object> result = new HashMap<>();

            if ("OK".equals(root.path("status").asText())) {
                result.put("success", true);
                result.put("predictions", root.path("predictions"));
            } else {
                result.put("success", false);
                result.put("predictions", new String[]{});
            }

            return result;
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "Erro ao buscar sugestões: " + e.getMessage());
            return errorResult;
        }
    }

    public Map<String, Object> getPlaceDetails(String placeId) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
                    .queryParam("place_id", placeId)
                    .queryParam("key", apiKey)
                    .queryParam("fields", "address_components,geometry,formatted_address")
                    .build()
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Map<String, Object> result = new HashMap<>();

            if ("OK".equals(root.path("status").asText())) {
                JsonNode placeResult = root.path("result");
                JsonNode location = placeResult.path("geometry").path("location");
                
                result.put("success", true);
                result.put("latitude", location.path("lat").asDouble());
                result.put("longitude", location.path("lng").asDouble());
                result.put("formattedAddress", placeResult.path("formatted_address").asText());
                result.put("addressComponents", placeResult.path("address_components"));
            } else {
                result.put("success", false);
                result.put("error", "Local não encontrado");
            }

            return result;
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "Erro ao buscar detalhes: " + e.getMessage());
            return errorResult;
        }
    }

    public Map<String, Object> calculateDistance(Double lat1, Double lng1, 
                                                  Double lat2, Double lng2) {
        try {
            String origin = lat1 + "," + lng1;
            String destination = lat2 + "," + lng2;

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json")
                    .queryParam("origins", origin)
                    .queryParam("destinations", destination)
                    .queryParam("key", apiKey)
                    .build()
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            Map<String, Object> result = new HashMap<>();

            if ("OK".equals(root.path("status").asText())) {
                JsonNode element = root.path("rows").get(0)
                        .path("elements").get(0);
                
                result.put("success", true);
                result.put("distance", element.path("distance").path("text").asText());
                result.put("distanceValue", element.path("distance").path("value").asInt());
                result.put("duration", element.path("duration").path("text").asText());
                result.put("durationValue", element.path("duration").path("value").asInt());
            } else {
                result.put("success", false);
                result.put("error", "Não foi possível calcular a distância");
            }

            return result;
        } catch (Exception e) {
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("error", "Erro ao calcular distância: " + e.getMessage());
            return errorResult;
        }
    }
}