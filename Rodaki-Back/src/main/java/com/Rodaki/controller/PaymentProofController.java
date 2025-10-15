package com.Rodaki.controller;

import com.Rodaki.dto.PaymentProofDTO;
import com.Rodaki.entity.PaymentProof.PaymentStatus;
import com.Rodaki.service.PaymentProofService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment-proofs")
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentProofController {

    private final PaymentProofService paymentProofService;

    public PaymentProofController(PaymentProofService paymentProofService) {
        this.paymentProofService = paymentProofService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PaymentProofDTO> upload(
            @RequestParam Long passengerId,
            @RequestParam Long driverId,
            @RequestParam BigDecimal amount,
            @RequestParam("file") MultipartFile file) {
        try {
            var proof = paymentProofService.upload(passengerId, driverId, file, amount);
            return ResponseEntity.ok(new PaymentProofDTO(proof));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload: " + e.getMessage());
        }
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<PaymentProofDTO>> findByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(paymentProofService.findByPassengerId(passengerId)
                .stream().map(PaymentProofDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<PaymentProofDTO>> findByDriver(@PathVariable Long driverId) {
        return ResponseEntity.ok(paymentProofService.findByDriverId(driverId)
                .stream().map(PaymentProofDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentProofDTO>> findByStatus(@PathVariable String status) {
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status);
        return ResponseEntity.ok(paymentProofService.findByStatus(paymentStatus)
                .stream().map(PaymentProofDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/driver/{driverId}/status/{status}")
    public ResponseEntity<List<PaymentProofDTO>> findByDriverAndStatus(
            @PathVariable Long driverId,
            @PathVariable String status) {
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status);
        return ResponseEntity.ok(paymentProofService.findByDriverAndStatus(driverId, paymentStatus)
                .stream().map(PaymentProofDTO::new).collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentProofDTO> updateStatus(@PathVariable Long id,
                                                        @RequestBody Map<String, String> request) {
        PaymentStatus status = PaymentStatus.valueOf(request.get("status"));
        var updated = paymentProofService.updateStatus(id, status);
        return ResponseEntity.ok(new PaymentProofDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentProofService.delete(id);
        return ResponseEntity.noContent().build();
    }
}