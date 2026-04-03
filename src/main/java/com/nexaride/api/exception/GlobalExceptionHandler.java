package com.nexaride.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔥 Seat conflict
    @ExceptionHandler(SeatNotAvailableException.class)
    public ResponseEntity<?> handleSeat(SeatNotAvailableException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 🔥 Not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 🔥 Invalid request
    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<?> handleInvalid(InvalidBookingException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 🔥 Validation errors
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(Exception ex) {
        return build(HttpStatus.BAD_REQUEST, "Validation failed");
    }

    // 🔴 Fallback (ONLY this should catch RuntimeException)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }

    private ResponseEntity<?> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", status.value(),
                        "error", status.getReasonPhrase(),
                        "message", message
                )
        );
    }
}