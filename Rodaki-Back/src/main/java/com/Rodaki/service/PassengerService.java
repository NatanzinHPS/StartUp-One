package com.Rodaki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Rodaki.entity.Passenger;
import com.Rodaki.entity.User;
import com.Rodaki.repository.PassengerRepository;
import com.Rodaki.repository.UserRepository;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;

    public PassengerService(PassengerRepository passengerRepository, UserRepository userRepository) {
        this.passengerRepository = passengerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Passenger salvar(Passenger passenger) {
        if (passenger.getUser() != null && passenger.getUser().getId() != null) {
            User user = userRepository.findById(passenger.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            passenger.setUser(user);
        }
        
        return passengerRepository.save(passenger);
    }

    public List<Passenger> listarTodos() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> buscarPorId(Long id) {
        return passengerRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Passageiro não encontrado");
        }
        passengerRepository.deleteById(id);
    }
}