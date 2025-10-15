package com.Rodaki.dto;

import com.Rodaki.entity.PaymentProof;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentProofDTO {
    private Long id;
    private Long passengerId;
    private String passengerName;
    private Long driverId;
    private String driverName;
    private String fileKey;
    private BigDecimal amount;
    private String status;
    private LocalDateTime uploadedAt;

    public PaymentProofDTO() {}

    public PaymentProofDTO(PaymentProof proof) {
        this.id = proof.getId();
        this.passengerId = proof.getPassenger().getId();
        this.passengerName = proof.getPassenger().getUser().getName();
        this.driverId = proof.getDriver().getId();
        this.driverName = proof.getDriver().getUser().getName();
        this.fileKey = proof.getFileKey();
        this.amount = proof.getAmount();
        this.status = proof.getStatus().name();
        this.uploadedAt = proof.getUploadedAt();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPassengerId() { return passengerId; }
    public void setPassengerId(Long passengerId) { this.passengerId = passengerId; }
    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }
    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }
    public String getFileKey() { return fileKey; }
    public void setFileKey(String fileKey) { this.fileKey = fileKey; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}