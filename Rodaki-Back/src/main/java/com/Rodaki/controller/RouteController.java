package com.Rodaki.controller;

import com.Rodaki.dto.RoutePointDTO;
import com.Rodaki.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "http://localhost:4200")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/driver/{driverId}/optimized")
    public ResponseEntity<Map<String, Object>> getOptimizedRoute(
            @PathVariable Long driverId,
            @RequestParam String period,
            @RequestParam String tripType
    ) {

        Map<String, Object> route = routeService.getOptimizedRoute(driverId, period, tripType);
        return ResponseEntity.ok(route);
    }

    @GetMapping("/driver/{driverId}/passengers")
    public ResponseEntity<List<RoutePointDTO>> getPassengerAddresses(
            @PathVariable Long driverId) {

        List<RoutePointDTO> addresses = routeService.getPassengerAddresses(driverId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/calculate-distance")
    public ResponseEntity<Map<String, Object>> calculateDistance(
            @RequestBody Map<String, Map<String, Double>> request) {

        Map<String, Double> origin = request.get("origin");
        Map<String, Double> destination = request.get("destination");

        Map<String, Object> result = routeService.calculateDistance(
            origin.get("lat"), origin.get("lng"),
            destination.get("lat"), destination.get("lng")
        );

        return ResponseEntity.ok(result);
    }
}