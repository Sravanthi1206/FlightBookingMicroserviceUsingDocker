package com.flightservice.controller;


import com.flightservice.entity.Airline;
import com.flightservice.service.FlightService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights/airlines")
public class AirlineController {

    private final FlightService service;
    public AirlineController(FlightService service) {
        this.service = service;
    }

    @PostMapping
    public Airline addAirline(@RequestBody Airline airline) {
        return service.addAirline(airline);
    }
}

