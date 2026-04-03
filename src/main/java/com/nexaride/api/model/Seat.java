package com.nexaride.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"busId", "seat_number"})
})
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long busId;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    private boolean booked;

    public Long getId() {
        return id;
    }

    public Long getBusId() {
        return busId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}