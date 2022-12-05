package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.dto.PageResult;
import io.codelex.flightplanner.flightmanager.dto.SearchFlightRequest;

import java.util.List;

public interface FlightService {
    void clearFlights();

    void addFlight(Flight req);

    void deleteFlight(Long id);

    default boolean isValidAirports(Flight req) {
        return !req.getFrom().getAirport().replaceAll("[^A-Za-z]", "").equalsIgnoreCase(req.getTo().getAirport().replaceAll("[^A-Za-z]", ""));
    }

    boolean isNotRepeated(Flight flight);

    default boolean isDateValid(Flight flight) {
        return flight.getDepartureTime().isBefore(flight.getArrivalTime());
    }

    List<Flight> flightList();

    List<Airport> findAirportByInput(String search);

    Flight findFlightsByID(Long id);

    PageResult searchFlights(SearchFlightRequest req);

}
