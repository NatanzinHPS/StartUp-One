package com.Rodaki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Rodaki.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}