package com.nexaride.api.repository;

import com.nexaride.api.model.Seat;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Normal queries
    List<Seat> findByBusId(Long busId);

    @Query("SELECT s FROM Seat s WHERE s.busId = :busId AND s.booked = false")
    List<Seat> findAvailableSeats(@Param("busId") Long busId);

    // 🔥 THIS is what you actually need
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.busId = :busId AND s.seatNumber = :seatNumber")
    Optional<Seat> findByBusIdAndSeatNumberForUpdate(@Param("busId") Long busId,
                                                    @Param("seatNumber") String seatNumber);
}