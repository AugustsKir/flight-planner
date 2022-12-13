package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.dto.PageResult;
import io.codelex.flightplanner.flightmanager.dto.SearchFlightRequest;
import io.codelex.flightplanner.flightmanager.repository.AirportRepository;
import io.codelex.flightplanner.flightmanager.repository.FlightRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "database")
public class FlightServiceDB extends AbstractFlightService implements FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightServiceDB(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public void clearData() {
        flightRepository.deleteAll();
        airportRepository.deleteAll();
    }

    @Transactional
    public Airport checkAirport(Airport airport) {
        Optional<Airport> airportOptional = airportRepository.findById(airport.getAirport());
        return airportOptional.orElseGet(() -> airportRepository.save(airport));
    }

    @Override
    @Transactional
    public void addFlight(Flight req) {
        req.setFrom(checkAirport(req.getFrom()));
        req.setTo(checkAirport(req.getTo()));
        flightRepository.save(req);
    }

    @Override
    @Transactional
    public synchronized void deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
        }
    }

    @Override
    public boolean isValidAirports(Flight req) {
        return super.isValidAirports(req);
    }

    @Override
    @Transactional
    public boolean isNotRepeated(Flight flight) {
        return !flightRepository.exists(Example.of(flight));
    }

    @Override
    public boolean isDateValid(Flight flight) {
        return super.isDateValid(flight);
    }

    @Override
    public List<Flight> flightList() {
        return flightRepository.findAll();
    }

    @Override
    public List<Airport> findAirportByInput(String input) {
        return airportRepository.findAirportByInput(input.toLowerCase().replaceAll(" ", ""));
    }

    @Override
    public Flight findFlightsByID(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest req) {
        if (!req.getFrom().equals(req.getTo())) {
            List<Flight> eligibleFlights = flightRepository.findFlights(req.getFrom(), req.getTo(), req.getDepartureDate().atStartOfDay());
            if (eligibleFlights.isEmpty()) {
                return new PageResult(0, 0, eligibleFlights);
            } else {
                return new PageResult(0, eligibleFlights.size(), eligibleFlights);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
