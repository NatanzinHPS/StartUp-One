package com.Rodaki.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Rodaki.dto.AuthResponse;
import com.Rodaki.dto.LoginRequest;
import com.Rodaki.dto.RegisterRequest;
import com.Rodaki.entity.Driver;
import com.Rodaki.entity.Passenger;
import com.Rodaki.entity.Role;
import com.Rodaki.entity.User;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.PassengerRepository;
import com.Rodaki.repository.UserRepository;
import com.Rodaki.util.JwtUtil;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PassengerRepository passengerRepository,
            DriverRepository driverRepository,
            PasswordEncoder passwordEncoder, 
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Validações
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new RuntimeException("Senha deve ter no mínimo 6 caracteres");
        }
        if (request.getRole() == null) {
            throw new RuntimeException("Role é obrigatória");
        }

        if (request.getRole() == Role.ROLE_ADMIN) {
            throw new RuntimeException("Não é permitido criar usuários Admin via registro");
        }

        User user = new User();
        user.setName(request.getName().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        
        user = userRepository.save(user);

        if (request.getRole() == Role.ROLE_PASSAGEIRO) {
            Passenger passenger = new Passenger(user);
            passengerRepository.save(passenger);
        } else if (request.getRole() == Role.ROLE_MOTORISTA) {
            Driver driver = new Driver(user);
            driverRepository.save(driver);
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse changeRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (newRole == Role.ROLE_ADMIN) {
            throw new RuntimeException("Não é permitido mudar para role Admin");
        }

        Role oldRole = user.getRole();

        if (oldRole == newRole) {
            throw new RuntimeException("Usuário já possui esta role");
        }

        if (oldRole == Role.ROLE_PASSAGEIRO) {
            passengerRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(p -> passengerRepository.delete(p));
        } else if (oldRole == Role.ROLE_MOTORISTA) {
            driverRepository.findAll().stream()
                .filter(d -> d.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(d -> driverRepository.delete(d));
        }

        if (newRole == Role.ROLE_PASSAGEIRO) {
            Passenger passenger = new Passenger(user);
            passengerRepository.save(passenger);
        } else if (newRole == Role.ROLE_MOTORISTA) {
            Driver driver = new Driver(user);
            driverRepository.save(driver);
        }

        user.setRole(newRole);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
        }

        User user = userRepository.findByEmail(request.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail().trim().toLowerCase(),
                request.getPassword()
            )
        );

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }
}