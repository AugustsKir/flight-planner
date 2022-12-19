package io.codelex.flightplanner.flightmanager.repository;

import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "database")
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("select f from Flight f where f.from.airport = :from and f.to.airport = :to and f.departureTime = :departure")
    List<Flight> findFlights(@Param("from") String from, @Param("to") String to, @Param("departure") LocalDateTime departure);
}