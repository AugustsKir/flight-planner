package io.codelex.flightplanner.flightmanager.repository;

import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "in-memory")
@Repository
public class InMemoryRepository {

    private final List<Flight> flightList = new ArrayList<Flight>();
    private Long nextID = 1L;

    public void clearFlights() {
        flightList.clear();
    }

    public synchronized void addFlight(Flight request) {
        request.setId(nextID);
        flightList.add(request);
        nextID++;
    }

    public synchronized void deleteFlight(Long id) {
        this.flightList.removeIf(f -> f.getId().equals(id));
    }

    public List<Flight> getFlights() {
        return flightList;
    }

}

