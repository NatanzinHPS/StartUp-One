package com.Rodaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Rodaki.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
