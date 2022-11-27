package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepo {
    List<Flight> flightList = new ArrayList<>();
    List<Airport> airportList = new ArrayList<>();


    public void clearFlights() {
        flightList.clear();
    }

    public void addFlight(Flight request) {
        flightList.add(request);
    }

    public void deleteFlight(Integer id) {
        this.flightList.removeIf(f -> f.getId().equals(id));
    }
}
