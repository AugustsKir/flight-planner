package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.dto.PageResult;
import io.codelex.flightplanner.flightmanager.dto.SearchFlightRequest;

import java.util.List;

public interface FlightService {
    void clearData();

    void addFlight(Flight req);

    void deleteFlight(Long id);

    boolean isValidAirports(Flight req);

    boolean isNotRepeated(Flight flight);

    boolean isDateValid(Flight flight);

    List<Flight> flightList();

    List<Airport> findAirportByInput(String search);

    Flight findFlightsByID(Long id);

    PageResult searchFlights(SearchFlightRequest req);

}
