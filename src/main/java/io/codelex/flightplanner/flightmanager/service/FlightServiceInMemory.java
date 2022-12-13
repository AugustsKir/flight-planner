package io.codelex.flightplanner.flightmanager.service;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.dto.PageResult;
import io.codelex.flightplanner.flightmanager.dto.SearchFlightRequest;
import io.codelex.flightplanner.flightmanager.repository.InMemoryRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "in-memory")
public class FlightServiceInMemory extends AbstractFlightService implements FlightService {


    private final InMemoryRepository repository;

    public FlightServiceInMemory(InMemoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void clearData() {
        repository.clearFlights();
    }

    @Override
    public void addFlight(Flight req) {
        repository.addFlight(req);
    }

    @Override
    public boolean isNotRepeated(Flight flight) {
        return repository.getFlights().stream().noneMatch(f -> f.equals(flight));
    }

    @Override
    public boolean isDateValid(Flight flight) {
        return super.isDateValid(flight);
    }

    @Override
    public List<Flight> flightList() {
        return repository.getFlights();
    }

    @Override
    public void deleteFlight(Long id) {
        repository.deleteFlight(id);
    }

    @Override
    public boolean isValidAirports(Flight req) {
        return super.isValidAirports(req);
    }

    @Override
    public List<Airport> findAirportByInput(String search) {
        String trimmedString = search.replaceAll(" ", "").toLowerCase();
        List<Airport> outList = new ArrayList<>();
        for (Flight flight : repository.getFlights()) {
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
    public synchronized Flight findFlightsByID(Long id) {
        List<Flight> output = repository.getFlights().stream().filter(f -> Objects.equals(f.getId(), id)).toList();
        if (output.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found! ");
        } else return output.get(0);
    }

    @Override
    public PageResult searchFlights(SearchFlightRequest req) {
        List<Flight> eligibleFlights = new ArrayList<>();
        if (req.getTo().equals(req.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (Flight flight : repository.getFlights()) {

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


