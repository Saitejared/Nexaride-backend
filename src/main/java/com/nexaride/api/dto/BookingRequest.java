package com.nexaride.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Bus ID is required")
    private Long busId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @NotBlank(message = "Payment mode is required")
    private String mode;

    // ================= GETTERS =================

    public Long getUserId() {
        return userId;
    }

    public Long getBusId() {
        return busId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getMode() {
        return mode;
    }

    // ================= SETTERS =================

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}