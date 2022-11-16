package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.domain.PageResult;
import io.codelex.flightplanner.flightmanager.domain.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepo repository;

    public FlightService(FlightRepo repository) {
        this.repository = repository;
    }

    public void clearFlights() {
        this.repository.clearFlights();
    }

    public void addFlight(Flight req) {
        this.repository.addFlight(req);
    }

    public boolean isValidAirports(Flight req) {
        return !req.getFrom().equals(req.getTo()) && this.repository.airportList.contains(req.getTo()) && this.repository.airportList.contains(req.getFrom());
    }

    public boolean isNotRepeated(Flight flight) {
        boolean isNotRep = true;
        int size = this.repository.flightList.stream().filter(f -> f.equals(flight)).toList().size();
        if (size > 0) {
            isNotRep = false;
        }
        return isNotRep;
    }

    public boolean isDateValid(Flight flight) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date arrival = formatter.parse(flight.getArrivalTime());
            Date departure = formatter.parse(flight.getDepartureTime());
            return departure.before(arrival);
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public List<Flight> flightList() {
        return this.repository.flightList;
    }

    public void deleteFlight(@PathVariable String id) {
        this.repository.deleteFlight(id);
    }

    public List<Airport> findAirportByInput(String search) {
        String trimmedString = search.replaceAll(" ", "").toLowerCase();
        List<Airport> outList = new ArrayList<>();
        for (Airport airport : repository.airportList) {
            if (airport.getAirport().toLowerCase().contains(trimmedString) ||
                    airport.getCity().toLowerCase().contains(trimmedString) ||
                    airport.getCountry().toLowerCase().contains(trimmedString))
                outList.add(airport);
        }
        if (outList.size() > 0) {
            return outList;
        } else {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    public Flight findFlightsByID(String id) {
        List<Flight> output = flightList().stream().filter(f -> f.getId() == Integer.parseInt(id)).toList();
        if (output.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found! ");
        } else return output.get(0);
    }

    public PageResult searchFlights(SearchFlightRequest req) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        List<Flight> eligibleFlights = new ArrayList<>();
        if (req.getTo().equals(req.getFrom())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        for (Flight flight : this.repository.flightList) {
            try {
                if (req.getFrom().equals(flight.getFrom().getAirport()) && req.getTo().equals(flight.getTo().getAirport()) && formatter.parse(req.getDepartureDate()).getDay() == formatter.parse(flight.getDepartureTime()).getDay()) {
                    eligibleFlights.add(flight);
                }
            } catch (ParseException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

        }
        if (eligibleFlights.isEmpty()) {
            return new PageResult(0, 0, eligibleFlights);
        } else {
            return new PageResult(0, eligibleFlights.size(), eligibleFlights);
        }
    }
}


