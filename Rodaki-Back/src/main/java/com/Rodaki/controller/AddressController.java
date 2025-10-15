package com.Rodaki.controller;

import com.Rodaki.dto.AddressDTO;
import com.Rodaki.entity.Address;
import com.Rodaki.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/passenger/{passengerId}")
    public ResponseEntity<AddressDTO> create(@PathVariable Long passengerId,
                                            @RequestBody Address address) {
        Address saved = addressService.save(address, passengerId);
        return ResponseEntity.ok(new AddressDTO(saved));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<AddressDTO>> findByPassenger(@PathVariable Long passengerId) {
        List<AddressDTO> addresses = addressService.findByPassengerId(passengerId)
                .stream()
                .map(AddressDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        Address address = addressService.findById(id);
        return ResponseEntity.ok(new AddressDTO(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable Long id,
                                            @RequestBody Address address) {
        Address updated = addressService.update(id, address);
        return ResponseEntity.ok(new AddressDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}