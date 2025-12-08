package com.flightservice.controller;

import com.flightservice.entity.Flight;
import com.flightservice.service.FlightService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

	private final FlightService service;

	public FlightController(FlightService service) {
		this.service = service;
	}

	@PostMapping("/{airlineId}")
	public Flight addFlight(@PathVariable Long airlineId, @RequestBody Flight flight) {
		return service.addFlight(flight, airlineId);
	}

	@GetMapping("/search")
	public List<Flight> search(@RequestParam String from, @RequestParam String to, @RequestParam String date) {
		return service.searchFlights(from, to, LocalDate.parse(date));
	}

	@GetMapping("/{id}")
	public Flight getFlight(@PathVariable Long id) {
		return service.getFlight(id);
	}
}
