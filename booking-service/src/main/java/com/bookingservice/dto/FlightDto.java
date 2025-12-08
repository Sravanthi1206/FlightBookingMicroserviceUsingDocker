package com.bookingservice.dto;

import java.time.LocalDateTime;
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
public class FlightDto {

    private Long id;
    private String flightNumber;
    private String fromPlace;
    private String toPlace;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
}
