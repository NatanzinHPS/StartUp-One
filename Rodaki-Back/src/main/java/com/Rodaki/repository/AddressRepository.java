package com.Rodaki.repository;

import com.Rodaki.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPassengerId(Long passengerId);
}