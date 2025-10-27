package com.Rodaki.controller;

import com.Rodaki.dto.AuthResponse;
import com.Rodaki.dto.UserDTO;
import com.Rodaki.entity.Role;
import com.Rodaki.entity.User;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.PassengerRepository;
import com.Rodaki.repository.UserRepository;
import com.Rodaki.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final AuthService authService;

    public UserController(
            UserRepository userRepository,
            PassengerRepository passengerRepository,
            DriverRepository driverRepository,
            AuthService authService) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.authService = authService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        UserDTO dto = new UserDTO(user);
        
        if (user.getRole() == Role.ROLE_PASSAGEIRO) {
            passengerRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(user.getId()))
                .findFirst()
                .ifPresent(p -> dto.setPassengerId(p.getId()));
        } else if (user.getRole() == Role.ROLE_MOTORISTA) {
            driverRepository.findAll().stream()
                .filter(d -> d.getUser().getId().equals(user.getId()))
                .findFirst()
                .ifPresent(d -> dto.setDriverId(d.getId()));
        }
        
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/change-role")
    public ResponseEntity<AuthResponse> changeRole(@RequestBody Map<String, String> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Role newRole = Role.valueOf(request.get("role"));
        
        AuthResponse response = authService.changeRole(user.getId(), newRole);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody Map<String, String> updates) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (updates.containsKey("name") && updates.get("name") != null) {
            user.setName(updates.get("name"));
        }
        if (updates.containsKey("phone") && updates.get("phone") != null) {
            user.setPhone(updates.get("phone"));
        }
        
        user = userRepository.save(user);
        
        return ResponseEntity.ok(new UserDTO(user));
    }
}