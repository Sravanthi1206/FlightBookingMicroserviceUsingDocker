package com.bookingservice.controller;
import com.bookingservice.dto.BookingRequestDto;
import com.bookingservice.dto.BookingResponseDto;
import com.bookingservice.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDto> book(@RequestBody BookingRequestDto req) {
        return ResponseEntity.ok(service.book(req));
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<BookingResponseDto> getByPnr(@PathVariable String pnr) {
        return ResponseEntity.ok(service.getByPnr(pnr));
    }

    @GetMapping("/history")
    public ResponseEntity<List<BookingResponseDto>> history(@RequestParam String email) {
        return ResponseEntity.ok(service.getHistory(email));
    }

    @DeleteMapping("/{pnr}")
    public ResponseEntity<Void> cancel(@PathVariable String pnr) {
        service.cancel(pnr);
        return ResponseEntity.noContent().build();
    }
}

