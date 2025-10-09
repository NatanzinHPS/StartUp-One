package com.Rodaki.dto;

import com.Rodaki.entity.Passageiro;

public class PassageiroDTO {
    private Long id;
    private UserDTO user;

    public PassageiroDTO() {}

    public PassageiroDTO(Passageiro passageiro) {
        this.id = passageiro.getId();
        this.user = new UserDTO(passageiro.getUser());
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
}
