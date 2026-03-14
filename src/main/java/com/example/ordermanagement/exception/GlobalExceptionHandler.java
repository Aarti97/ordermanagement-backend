package com.example.ordermanagement.exception;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /* ================= DUPLICATE / UNIQUE ERROR ================= */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicate(
            DataIntegrityViolationException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                    "message",
                    "Customer already exists"
                ));
    }

    /* ================= RUNTIME ERROR ================= */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(
            RuntimeException ex) {

        return ResponseEntity
                .badRequest()
                .body(Map.of(
                    "message",
                    ex.getMessage()
                ));
    }

    /* ================= GENERIC ERROR ================= */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(
            Exception ex) {

        return ResponseEntity
                .internalServerError()
                .body(Map.of(
                    "message",
                    "Server error. Try again later"
                ));
    }
}
