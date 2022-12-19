package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Flight;

public abstract class AbstractFlightService {

    boolean isValidAirports(Flight req) {
        return !req.getFrom().getAirport().replaceAll("[^A-Za-z]", "").equalsIgnoreCase(req.getTo().getAirport().replaceAll("[^A-Za-z]", ""));
    }
    boolean isDateValid(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }
}
