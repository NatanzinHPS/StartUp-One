package com.Rodaki.service;

import com.Rodaki.dto.RoutePointDTO;
import com.Rodaki.entity.Address;
import com.Rodaki.entity.Driver;
import com.Rodaki.entity.Passenger;
import com.Rodaki.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private final DriverRepository driverRepository;
    private final GoogleMapsService googleMapsService;

    public RouteService(DriverRepository driverRepository,
                       GoogleMapsService googleMapsService) {
        this.driverRepository = driverRepository;
        this.googleMapsService = googleMapsService;
    }

    public Map<String, Object> getOptimizedRoute(Long driverId, String period, String tripType) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();

        List<RoutePointDTO> routePoints = driver.getPassengers().stream()
                .filter(passenger -> hasScheduleForDay(passenger, currentDayOfWeek, period, tripType))
                .flatMap(passenger -> passenger.getAddresses().stream()
                        .map(address -> createRoutePoint(passenger, address, period)))
                .collect(Collectors.toList());

       List<RoutePointDTO> optimizedRoute = optimizeRoute(routePoints);

        Map<String, Object> routeInfo = calculateRouteInfo(optimizedRoute);

        Map<String, Object> result = new HashMap<>();
        result.put("waypoints", optimizedRoute);
        result.put("totalDistance", routeInfo.get("totalDistance"));
        result.put("totalDuration", routeInfo.get("totalDuration"));
        result.put("passengerCount", optimizedRoute.size());

        return result;
    }

    public List<RoutePointDTO> getPassengerAddresses(Long driverId) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return driver.getPassengers().stream()
                .flatMap(passenger -> passenger.getAddresses().stream()
                        .map(address -> createRoutePoint(passenger, address, "ALL_PERIODS")))
                .collect(Collectors.toList());
    }

    public Map<String, Object> calculateDistance(Double lat1, Double lng1,
                                                  Double lat2, Double lng2) {
        return googleMapsService.calculateDistance(lat1, lng1, lat2, lng2);
    }

    private boolean hasScheduleForDay(Passenger passenger, int dayOfWeek, String period, String tripType) {
        if (!tripType.equals("ONE_WAY") && !tripType.equals("RETURN")) {
             throw new IllegalArgumentException("Trip type for optimization must be ONE_WAY or RETURN");
        }

        return passenger.getSchedules().stream()
                .anyMatch(s -> s.getIsActive()
                        && s.getDayOfWeek() == dayOfWeek
                        && s.getPeriod().name().equals(period)
                        && (s.getTripType().name().equals(tripType)
                            || s.getTripType().name().equals("ROUND_TRIP")));
    }

    private RoutePointDTO createRoutePoint(Passenger passenger, Address address, String period) {
        RoutePointDTO point = new RoutePointDTO();
        point.setPassengerId(passenger.getId());
        point.setPassengerName(passenger.getUser().getName());
        point.setAddress(address.getFullAddress());
        point.setLatitude(address.getLatitude());
        point.setLongitude(address.getLongitude());
        point.setSchedule(period);
        point.setPhone(passenger.getUser().getPhone());
        return point;
    }

    private List<RoutePointDTO> optimizeRoute(List<RoutePointDTO> points) {
        if (points.isEmpty()) {
            return points;
        }

        List<RoutePointDTO> optimized = new ArrayList<>();
        List<RoutePointDTO> remaining = new ArrayList<>(points);

       RoutePointDTO current = remaining.remove(0);
        current.setOrder(1);
        optimized.add(current);

        int order = 2;
        while (!remaining.isEmpty()) {
            RoutePointDTO nearest = findNearest(current, remaining);
            nearest.setOrder(order++);
            optimized.add(nearest);
            remaining.remove(nearest);
            current = nearest;
        }

        return optimized;
    }

    private RoutePointDTO findNearest(RoutePointDTO current, List<RoutePointDTO> points) {
        return points.stream()
                .min(Comparator.comparingDouble(p ->
                    calculateHaversineDistance(
                        current.getLatitude(), current.getLongitude(),
                        p.getLatitude(), p.getLongitude()
                    )))
                .orElse(points.get(0));
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private Map<String, Object> calculateRouteInfo(List<RoutePointDTO> route) {
        double totalDistance = 0;

        for (int i = 0; i < route.size() - 1; i++) {
            RoutePointDTO current = route.get(i);
            RoutePointDTO next = route.get(i + 1);

            totalDistance += calculateHaversineDistance(
                current.getLatitude(), current.getLongitude(),
                next.getLatitude(), next.getLongitude()
            );
        }

        double estimatedHours = totalDistance / 40.0;
        int minutes = (int) (estimatedHours * 60);

        Map<String, Object> info = new HashMap<>();
        info.put("totalDistance", String.format("%.2f km", totalDistance));
        info.put("totalDuration", String.format("%d min", minutes));

        return info;
    }
}