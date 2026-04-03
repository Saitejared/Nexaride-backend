package com.nexaride.api.controller;

import com.nexaride.api.dto.BookingRequest;
import com.nexaride.api.dto.BookingResponse;
import com.nexaride.api.dto.BookingHistoryResponse;
import com.nexaride.api.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // ✅ BOOK
    @PostMapping
    public ResponseEntity<BookingResponse> book(@Valid @RequestBody BookingRequest request) {

        BookingResponse response = service.book(
                request.getUserId(),
                request.getBusId(),
                request.getSeatNumber(),
                request.getAmount(),
                request.getMode()
        );

        return ResponseEntity.ok(response);
    }

    // ✅ CANCEL
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancelBooking(id));
    }

    // 🔥 PAGINATION (FIXED WITH DTO)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BookingHistoryResponse>> getUserBookings(
            @PathVariable Long userId,
            @PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(service.getUserBookings(userId, pageable));
    }
}