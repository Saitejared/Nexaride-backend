package com.nexaride.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexaride.api.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}