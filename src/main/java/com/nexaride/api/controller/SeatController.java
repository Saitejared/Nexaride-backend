package com.nexaride.api.controller;

import com.nexaride.api.model.Seat;
import com.nexaride.api.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatRepository seatRepo;

    public SeatController(SeatRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    // ✅ Create seat
    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {
        return seatRepo.save(seat);
    }

    // ✅ Get all seats by bus
    @GetMapping("/{busId}")
    public List<Seat> getSeats(@PathVariable Long busId) {
        return seatRepo.findByBusId(busId);
    }

    // ✅ Get available seats
    @GetMapping("/available/{busId}")
    public List<Seat> getAvailableSeats(@PathVariable Long busId) {
        return seatRepo.findAvailableSeats(busId);
    }
}