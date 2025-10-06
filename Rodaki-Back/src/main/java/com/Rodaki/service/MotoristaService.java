package com.Rodaki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Rodaki.entity.Motorista;
import com.Rodaki.entity.User;
import com.Rodaki.repository.MotoristaRepository;
import com.Rodaki.repository.UserRepository;

@Service
public class MotoristaService {
    private final MotoristaRepository motoristaRepository;
    private final UserRepository userRepository;

    public MotoristaService(MotoristaRepository motoristaRepository, UserRepository userRepository) {
        this.motoristaRepository = motoristaRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Motorista salvar(Motorista motorista) {
        // Validar se o usuário existe
        if (motorista.getUser() != null && motorista.getUser().getId() != null) {
            User user = userRepository.findById(motorista.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            motorista.setUser(user);
        }
        
        return motoristaRepository.save(motorista);
    }

    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    public Optional<Motorista> buscarPorId(Long id) {
        return motoristaRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        if (!motoristaRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado");
        }
        motoristaRepository.deleteById(id);
    }
}