package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.dto.PageResult;
import io.codelex.flightplanner.flightmanager.dto.SearchFlightRequest;
import io.codelex.flightplanner.flightmanager.repository.AirportRepository;
import io.codelex.flightplanner.flightmanager.repository.FlightRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "database")
public class FlightServiceDB implements FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    public FlightServiceDB(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public void clearFlights() {
        flightRepository.deleteAll();
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
    public boolean isNotRepeated(Flight flight) {
        return flightRepository.findAll().stream().noneMatch(f -> f.equals(flight));
    }

    @Override
    public List<Flight> flightList() {
        return flightRepository.findAll();
    }

    @Override
    public List<Airport> findAirportByInput(String search) {
        String trimmedString = search.replaceAll(" ", "").toLowerCase();
        List<Airport> outList = new ArrayList<>();
        for (Flight flight : flightRepository.findAll()) {
            if (flight.getFrom().getAirport().toLowerCase().contains(trimmedString) ||
                    flight.getFrom().getCity().toLowerCase().contains(trimmedString) ||
                    flight.getFrom().getCountry().toLowerCase().contains(trimmedString)) {
                outList.add(flight.getFrom());
            }
        }
        if (outList.size() > 0) {
            return outList;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Flight findFlightsByID(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest req) {
        List<Flight> eligibleFlights = new ArrayList<>();
        if (req.getTo().equals(req.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (Flight flight : flightRepository.findAll()) {
            if (req.getFrom().equals(flight.getFrom().getAirport())
                    && req.getTo().equals(flight.getTo().getAirport())
                    && flight.getDepartureTime().getDayOfYear() == req.getDepartureDate().getDayOfYear()) {
                eligibleFlights.add(flight);
            }

        }
        if (eligibleFlights.isEmpty()) {
            return new PageResult(0, 0, eligibleFlights);
        } else {
            return new PageResult(0, eligibleFlights.size(), eligibleFlights);
        }
    }
}
