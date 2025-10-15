package com.Rodaki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Rodaki.entity.Driver;
import com.Rodaki.entity.User;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.UserRepository;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public DriverService(DriverRepository driverRepository, UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Driver salvar(Driver driver) {
        if (driver.getUser() != null && driver.getUser().getId() != null) {
            User user = userRepository.findById(driver.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            driver.setUser(user);
        }
        
        return driverRepository.save(driver);
    }

    public List<Driver> listarTodos() {
        return driverRepository.findAll();
    }

    public Optional<Driver> buscarPorId(Long id) {
        return driverRepository.findById(id);
    }

    @Transactional
    public void deletar(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new RuntimeException("Motorista não encontrado");
        }
        driverRepository.deleteById(id);
    }
}