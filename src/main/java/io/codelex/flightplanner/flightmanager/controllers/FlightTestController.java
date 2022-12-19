package io.codelex.flightplanner.flightmanager.controllers;

import io.codelex.flightplanner.flightmanager.service.FlightService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class FlightTestController {
    private final FlightService flightService;

    public FlightTestController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/clear")
    public void clearFlights() {
        flightService.clearData();
    }
}
