package com.bookingservice.dto;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingResponseDto {

    private String pnr;
    private Long flightId;
    private String email;
    private LocalDate journeyDate;
    private String mealType;
    private String status;
    private Double totalAmount;
    private List<PassengerDto> passengers;
}

