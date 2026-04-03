package com.nexaride.api.dto;

public class BookingHistoryResponse {

    private Long bookingId;
    private String seatNumber;
    private double amount;
    private String status;

    public BookingHistoryResponse(Long bookingId, String seatNumber, double amount, String status) {
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.amount = amount;
        this.status = status;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}