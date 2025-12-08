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
public class BookingRequestDto {
	    private Long flightId;
	    private String email;
	    private String mealType;     
	    private LocalDate journeyDate;
	    private List<PassengerDto> passengers;
}

