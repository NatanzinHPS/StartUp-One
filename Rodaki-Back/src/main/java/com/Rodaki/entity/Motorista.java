package com.Rodaki.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "motoristas")
public class Motorista {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String veiculo;
    private String placa;
    private Integer capacidade;

    private Double latitude;
    private Double longitude;
    
    private LocalDateTime createdAt;

    public Motorista() {
        this.createdAt = LocalDateTime.now();
    }

    public Motorista(User user, String veiculo, String placa, Integer capacidade, Double latitude, Double longitude) {
        this.user = user;
        this.veiculo = veiculo;
        this.placa = placa;
        this.capacidade = capacidade;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}