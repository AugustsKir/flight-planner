package io.codelex.flightplanner.flightmanager.repository;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@ConditionalOnProperty(prefix = "flight-app", name = "storage", havingValue = "database")
@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {
    @Query("SELECT a from Airport a " +
            "where lower(a.airport ) like concat('%', :name, '%') " +
            "or lower(a.city)  like concat('%', :name, '%')" +
            "or lower(a.country) like concat('%', :name, '%')")
    List<Airport> findAirportByInput(@Param("name") String name);
}