package com.Rodaki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Rodaki.entity.Passageiro;
import com.Rodaki.entity.User;
import com.Rodaki.repository.PassageiroRepository;
import com.Rodaki.repository.UserRepository;

@Service
public class PassageiroService {
    private final PassageiroRepository passageiroRepository;
    private final UserRepository userRepository;

    public PassageiroService(PassageiroRepository passageiroRepository, UserRepository userRepository) {
        this.passageiroRepository = passageiroRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Passageiro salvar(Passageiro passageiro) {
        // Validar se o usuário existe
        if (passageiro.getUser() != null && passageiro.getUser().getId() != null) {
            User user = userRepository.findById(passageiro.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            passageiro.setUser(user);
        }
        
        return passageiroRepository.save(passageiro);
    }

    public List<Passageiro> listarTodos() {
        return passageiroRepository.findAll();
    }

    public Optional<Passageiro> buscarPorId(Long id) {
        return passageiroRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        if (!passageiroRepository.existsById(id)) {
            throw new RuntimeException("Passageiro não encontrado");
        }
        passageiroRepository.deleteById(id);
    }
}