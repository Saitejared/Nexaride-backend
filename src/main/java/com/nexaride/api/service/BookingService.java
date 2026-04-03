package com.nexaride.api.service;

import com.nexaride.api.dto.BookingHistoryResponse;
import com.nexaride.api.dto.BookingResponse;
import com.nexaride.api.exception.InvalidBookingException;
import com.nexaride.api.exception.ResourceNotFoundException;
import com.nexaride.api.exception.SeatNotAvailableException;
import com.nexaride.api.model.Booking;
import com.nexaride.api.model.Payment;
import com.nexaride.api.model.Seat;
import com.nexaride.api.model.BookingStatus;
import com.nexaride.api.repository.BookingRepository;
import com.nexaride.api.repository.PaymentRepository;
import com.nexaride.api.repository.SeatRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepo;
    private final PaymentRepository paymentRepo;
    private final SeatRepository seatRepo;

    public BookingService(BookingRepository bookingRepo,
                          PaymentRepository paymentRepo,
                          SeatRepository seatRepo) {
        this.bookingRepo = bookingRepo;
        this.paymentRepo = paymentRepo;
        this.seatRepo = seatRepo;
    }

    // ===================== BOOK SEAT =====================

    @Transactional
    public BookingResponse book(Long userId,
                                Long busId,
                                String seatNumber,
                                double amount,
                                String mode) {

        log.info("Booking request: userId={}, busId={}, seat={}", userId, busId, seatNumber);

        Seat seat = seatRepo.findByBusIdAndSeatNumberForUpdate(busId, seatNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        if (seat.isBooked()) {
            throw new SeatNotAvailableException("Seat already booked");
        }

        seat.setBooked(true);

        Booking booking = createBooking(userId, busId, seat, amount);

        log.info("Booking created: bookingId={}", booking.getBookingId());

        createPayment(booking, amount, mode);

        return new BookingResponse(
                "SUCCESS",
                "Seat booked successfully",
                seat.getSeatNumber(),
                booking.getBookingId()
        );
    }

    // ===================== CANCEL BOOKING =====================

    @Transactional
    public String cancelBooking(Long bookingId) {

        log.info("Cancel request: bookingId={}", bookingId);

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new InvalidBookingException("Booking already cancelled");
        }

        Seat seat = seatRepo.findByBusIdAndSeatNumberForUpdate(
                booking.getBusId(),
                booking.getSeatNumber()
        ).orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        seat.setBooked(false);

        booking.setStatus(BookingStatus.CANCELLED);

        log.info("Booking cancelled: bookingId={}", bookingId);

        return "Booking cancelled successfully";
    }

    // ===================== PAGINATION + DTO =====================

    public Page<BookingHistoryResponse> getUserBookings(Long userId, Pageable pageable) {

        Page<Booking> bookings = bookingRepo.findByUserId(userId, pageable);

        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found for user");
        }

        return bookings.map(b -> new BookingHistoryResponse(
                b.getBookingId(),
                b.getSeatNumber(),
                b.getTotalAmount(),
                b.getStatus().name()
        ));
    }

    // ===================== HELPERS =====================

    private Booking createBooking(Long userId,
                                  Long busId,
                                  Seat seat,
                                  double amount) {

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setBusId(busId);
        booking.setSeatNumber(seat.getSeatNumber());
        booking.setTotalAmount(amount);
        booking.setStatus(BookingStatus.CONFIRMED);

        return bookingRepo.save(booking);
    }

    private void createPayment(Booking booking,
                               double amount,
                               String mode) {

        Payment payment = new Payment();
        payment.setBookingId(booking.getBookingId());
        payment.setAmount(amount);
        payment.setPaymentMode(mode);

        boolean success = new Random().nextBoolean();

        if (success) {
            payment.setStatus("SUCCESS");
            log.info("Payment SUCCESS for bookingId={}", booking.getBookingId());
        } else {
            payment.setStatus("FAILED");
            log.error("Payment FAILED for bookingId={}", booking.getBookingId());

            // 🔥 rollback entire transaction
            throw new RuntimeException("Payment failed");
        }

        paymentRepo.save(payment);
    }
}