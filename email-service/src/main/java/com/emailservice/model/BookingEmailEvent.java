package com.emailservice.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingEmailEvent {
    private String pnr;
    private Long flightId;
    private String email;
    private LocalDate journeyDate;
    private String mealType;
    private Double totalAmount;
    private List<PassengerDto> passengers;
}

