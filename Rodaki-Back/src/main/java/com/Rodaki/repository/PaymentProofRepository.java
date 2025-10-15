package com.Rodaki.repository;

import com.Rodaki.entity.PaymentProof;
import com.Rodaki.entity.PaymentProof.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentProofRepository extends JpaRepository<PaymentProof, Long> {
    List<PaymentProof> findByPassengerId(Long passengerId);
    List<PaymentProof> findByDriverId(Long driverId);
    List<PaymentProof> findByStatus(PaymentStatus status);
    List<PaymentProof> findByDriverIdAndStatus(Long driverId, PaymentStatus status);
}