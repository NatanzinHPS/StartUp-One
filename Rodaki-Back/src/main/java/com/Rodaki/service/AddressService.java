package com.Rodaki.service;

import com.Rodaki.entity.Address;
import com.Rodaki.entity.Passenger;
import com.Rodaki.repository.AddressRepository;
import com.Rodaki.repository.PassengerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final PassengerRepository passengerRepository;
    private final GoogleMapsService googleMapsService;

    public AddressService(AddressRepository addressRepository, 
                         PassengerRepository passengerRepository,
                         GoogleMapsService googleMapsService) {
        this.addressRepository = addressRepository;
        this.passengerRepository = passengerRepository;
        this.googleMapsService = googleMapsService;
    }

    @Transactional
    public Address save(Address address, Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));

        address.setPassenger(passenger);

        // Se latitude/longitude não foram fornecidos, busca via Google Maps
        if (address.getLatitude() == null || address.getLongitude() == null) {
            String fullAddress = address.getFullAddress();
            Map<String, Object> geocodeResult = googleMapsService.geocodeAddress(fullAddress);
            
            if (Boolean.TRUE.equals(geocodeResult.get("success"))) {
                address.setLatitude((Double) geocodeResult.get("latitude"));
                address.setLongitude((Double) geocodeResult.get("longitude"));
            }
        }

        return addressRepository.save(address);
    }

    public List<Address> findByPassengerId(Long passengerId) {
        return addressRepository.findByPassengerId(passengerId);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }

    @Transactional
    public Address update(Long id, Address updatedAddress) {
        Address address = findById(id);
        
        address.setStreet(updatedAddress.getStreet());
        address.setNumberStreet(updatedAddress.getNumberStreet());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setCep(updatedAddress.getCep());

        // Atualiza coordenadas
        String fullAddress = address.getFullAddress();
        Map<String, Object> geocodeResult = googleMapsService.geocodeAddress(fullAddress);
        
        if (Boolean.TRUE.equals(geocodeResult.get("success"))) {
            address.setLatitude((Double) geocodeResult.get("latitude"));
            address.setLongitude((Double) geocodeResult.get("longitude"));
        }

        return addressRepository.save(address);
    }

    @Transactional
    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado");
        }
        addressRepository.deleteById(id);
    }
}