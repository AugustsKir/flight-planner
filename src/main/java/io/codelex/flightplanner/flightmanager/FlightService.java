package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.domain.PageResult;
import io.codelex.flightplanner.flightmanager.domain.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FlightService {
    private final FlightRepo repository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FlightService(FlightRepo repository) {
        this.repository = repository;
    }

    public void clearFlights() {
        repository.clearFlights();
    }

    public void addFlight(Flight req) {
        repository.addFlight(req);
    }

    public boolean isValidAirports(Flight req) {
        return !req.getFrom().getAirport().replaceAll("[^A-Za-z]", "").equalsIgnoreCase(req.getTo().getAirport().replaceAll("[^A-Za-z]", ""));
    }

    public boolean isNotRepeated(Flight flight) {
        return flightList().stream().noneMatch(f -> f.equals(flight));
    }

    public boolean isDateValid(Flight flight) {
        LocalDateTime arrival = LocalDateTime.parse(flight.getArrivalTime(), formatter);
        LocalDateTime departure = LocalDateTime.parse(flight.getDepartureTime(), formatter);
        return departure.isBefore(arrival);

    }

    public List<Flight> flightList() {
        return repository.flightList;
    }

    public void deleteFlight(Integer id) {
        repository.deleteFlight(id);
    }

    public List<Airport> findAirportByInput(String search) {
        String trimmedString = search.replaceAll(" ", "").toLowerCase();
        List<Airport> outList = new ArrayList<>();
        for (Flight flight : repository.flightList) {
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

    public Flight findFlightsByID(Integer id) {
        List<Flight> output = flightList().stream().filter(f -> Objects.equals(f.getId(), id)).toList();
        if (output.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found! ");
        } else return output.get(0);
    }

    public PageResult searchFlights(SearchFlightRequest req) {
        List<Flight> eligibleFlights = new ArrayList<>();
        if (req.getTo().equals(req.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (Flight flight : repository.flightList) {

            if (req.getFrom().equals(flight.getFrom().getAirport())
                    && req.getTo().equals(flight.getTo().getAirport())
                    && LocalDateTime.parse(flight.getDepartureTime(), formatter).getDayOfYear() == LocalDateTime.parse(req.getDepartureDate(), formatter).getDayOfYear()) {
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


