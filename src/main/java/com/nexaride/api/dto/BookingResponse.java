package com.nexaride.api.dto;

public class BookingResponse {

    private final String status;
    private final String message;
    private final String seatNumber;
    private final Long bookingId;

    public BookingResponse(String status, String message,
                           String seatNumber, Long bookingId) {
        this.status = status;
        this.message = message;
        this.seatNumber = seatNumber;
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public Long getBookingId() {
        return bookingId;
    }
}