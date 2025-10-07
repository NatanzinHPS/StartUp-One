package com.Rodaki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Rodaki.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
    List<Endereco> findByPassageiroId(Long passageiroId);
    
    Optional<Endereco> findByPassageiroIdAndIsPrincipalTrue(Long passageiroId);
    
    List<Endereco> findByPassageiroIdOrderByIsPrincipalDescCreatedAtDesc(Long passageiroId);
    
    @Modifying
    @Query("UPDATE Endereco e SET e.isPrincipal = false WHERE e.passageiro.id = :passageiroId")
    void desmarcarTodosPrincipais(@Param("passageiroId") Long passageiroId);
    
    boolean existsByPassageiroIdAndIsPrincipalTrue(Long passageiroId);
}