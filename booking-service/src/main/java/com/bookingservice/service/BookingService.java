package com.bookingservice.service;


import com.bookingservice.client.FlightClient;
import com.bookingservice.dto.BookingRequestDto;
import com.bookingservice.dto.BookingResponseDto;
import com.bookingservice.dto.FlightDto;
import com.bookingservice.dto.PassengerDto;
import com.bookingservice.kafka.BookingEmailEvent;
import com.bookingservice.entity.Booking;
import com.bookingservice.entity.Passenger;
import com.bookingservice.repo.BookingRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final FlightClient flightClient;
    private final KafkaTemplate<String, BookingEmailEvent> kafkaTemplate;

    @Value("${booking.kafka.topic}")
    private String bookingTopic;

    public BookingService(BookingRepository bookingRepo,
                          FlightClient flightClient,
                          KafkaTemplate<String, BookingEmailEvent> kafkaTemplate) {
        this.bookingRepo = bookingRepo;
        this.flightClient = flightClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public BookingResponseDto book(BookingRequestDto req) {
        FlightDto flight = flightClient.getFlight(req.getFlightId());
        if (flight == null) {
            throw new RuntimeException("Flight not found");
        }
        int seatCount = req.getPassengers().size();
        double totalAmount = flight.getPrice() * seatCount;
        Booking booking = new Booking();
        booking.setPnr(generatePnr());
        booking.setFlightId(req.getFlightId());
        booking.setUserEmail(req.getEmail());
        booking.setJourneyDate(req.getJourneyDate());
        booking.setMealType(req.getMealType());
        booking.setStatus("BOOKED");
        booking.setTotalAmount(totalAmount);
        List<Passenger> passengers = req.getPassengers().stream()
                .map(pdto -> {
                    Passenger p = new Passenger();
                    p.setName(pdto.getName());
                    p.setGender(pdto.getGender());
                    p.setAge(pdto.getAge());
                    p.setSeatNumber(pdto.getSeatNumber());
                    p.setBooking(booking);
                    return p;
                })
                .collect(Collectors.toList());

        booking.setPassengers(passengers);
        Booking saved = bookingRepo.save(booking);
        BookingEmailEvent event = new BookingEmailEvent(
                saved.getPnr(),
                saved.getFlightId(),
                saved.getUserEmail(),
                saved.getJourneyDate(),
                saved.getMealType(),
                saved.getTotalAmount(),
                req.getPassengers()
        );

        kafkaTemplate.send(bookingTopic, saved.getPnr(), event);
        return mapToResponse(saved);
    }

    public BookingResponseDto getByPnr(String pnr) {
        Booking booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return mapToResponse(booking);
    }

    public List<BookingResponseDto> getHistory(String email) {
        return bookingRepo.findByUserEmail(email).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancel(String pnr) {
        Booking booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus("CANCELLED");
        bookingRepo.save(booking);
        // Optionally send cancellation event to Kafka too
    }

    private String generatePnr() {
        return "PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BookingResponseDto mapToResponse(Booking b) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setPnr(b.getPnr());
        dto.setFlightId(b.getFlightId());
        dto.setEmail(b.getUserEmail());
        dto.setJourneyDate(b.getJourneyDate());
        dto.setMealType(b.getMealType());
        dto.setStatus(b.getStatus());
        dto.setTotalAmount(b.getTotalAmount());

        List<PassengerDto> passengers = b.getPassengers().stream().map(p -> {
            PassengerDto pd = new PassengerDto();
            pd.setName(p.getName());
            pd.setGender(p.getGender());
            pd.setAge(p.getAge());
            pd.setSeatNumber(p.getSeatNumber());
            return pd;
        }).collect(Collectors.toList());

        dto.setPassengers(passengers);
        return dto;
    }
}

