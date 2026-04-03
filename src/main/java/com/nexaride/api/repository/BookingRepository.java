package com.nexaride.api.repository;

import com.nexaride.api.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // 🔥 Pagination support
    Page<Booking> findByUserId(Long userId, Pageable pageable);
}