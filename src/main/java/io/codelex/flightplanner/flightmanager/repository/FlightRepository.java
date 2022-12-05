package io.codelex.flightplanner.flightmanager.repository;

import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "database")
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}