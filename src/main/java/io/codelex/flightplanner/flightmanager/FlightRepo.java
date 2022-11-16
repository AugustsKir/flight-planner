package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepo {
    private Airport RIX = new Airport("Latvia", "Riga", "RIX");
    private Airport DME = new Airport("Russia", "Moscow", "DME");

    private Airport DXB = new Airport("United Arab Emirates", "Dubai", "DXB");
    private Airport ARN = new Airport("Sweden", "Stockholm", "ARN");
    List<Flight> flightList = new ArrayList<>();
    List<Airport> airportList = new ArrayList<>();

    public FlightRepo() {
        airportList.add(RIX);
        airportList.add(DME);
        airportList.add(DXB);
        airportList.add(ARN);
    }

    public void clearFlights() {
        flightList.clear();
    }

    public void addFlight(Flight request) {
        flightList.add(request);
    }

    public void deleteFlight(@PathVariable String id) {
        this.flightList.removeIf(f -> f.getId() == Integer.parseInt(id));
    }
}
