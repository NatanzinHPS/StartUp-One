package com.Rodaki.service;

import com.Rodaki.entity.Driver;
import com.Rodaki.entity.Passenger;
import com.Rodaki.entity.PaymentProof;
import com.Rodaki.entity.PaymentProof.PaymentStatus;
import com.Rodaki.repository.DriverRepository;
import com.Rodaki.repository.PassengerRepository;
import com.Rodaki.repository.PaymentProofRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentProofService {

    private final PaymentProofRepository paymentProofRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final MinioService minioService;

    public PaymentProofService(PaymentProofRepository paymentProofRepository,
                              PassengerRepository passengerRepository,
                              DriverRepository driverRepository,
                              MinioService minioService) {
        this.paymentProofRepository = paymentProofRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.minioService = minioService;
    }

    @Transactional
    public PaymentProof upload(Long passengerId, Long driverId, 
                              MultipartFile file, BigDecimal amount) throws Exception {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passageiro não encontrado"));
        
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado"));

        // Gera nome do arquivo
        String fileName = "payment-proof-" + passengerId + "-" + 
                         System.currentTimeMillis() + "-" + file.getOriginalFilename();

        // Upload para MinIO
        String fileKey = minioService.uploadFile(
            fileName, 
            file.getInputStream(), 
            file.getContentType()
        );

        PaymentProof proof = new PaymentProof(
            passenger, 
            driver, 
            fileKey, 
            amount, 
            PaymentStatus.PENDING
        );

        return paymentProofRepository.save(proof);
    }

    public List<PaymentProof> findByPassengerId(Long passengerId) {
        return paymentProofRepository.findByPassengerId(passengerId);
    }

    public List<PaymentProof> findByDriverId(Long driverId) {
        return paymentProofRepository.findByDriverId(driverId);
    }

    public List<PaymentProof> findByStatus(PaymentStatus status) {
        return paymentProofRepository.findByStatus(status);
    }

    public List<PaymentProof> findByDriverAndStatus(Long driverId, PaymentStatus status) {
        return paymentProofRepository.findByDriverIdAndStatus(driverId, status);
    }

    @Transactional
    public PaymentProof updateStatus(Long id, PaymentStatus status) {
        PaymentProof proof = paymentProofRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprovante não encontrado"));
        
        proof.setStatus(status);
        return paymentProofRepository.save(proof);
    }

    @Transactional
    public void delete(Long id) {
        if (!paymentProofRepository.existsById(id)) {
            throw new RuntimeException("Comprovante não encontrado");
        }
        paymentProofRepository.deleteById(id);
    }
}