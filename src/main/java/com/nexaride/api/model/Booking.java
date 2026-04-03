package com.nexaride.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;
    private Long busId;

    private String seatNumber;

    private double totalAmount;

    // 🔥 FIXED PROPERLY
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // ================= GETTERS =================

    public Long getBookingId() {
        return bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBusId() {
        return busId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    // ================= SETTERS =================

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}