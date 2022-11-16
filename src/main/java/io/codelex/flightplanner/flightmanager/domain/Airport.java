package io.codelex.flightplanner.flightmanager.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Airport {
    @NotBlank
    private final String country;
    @NotBlank
    private final String city;
    @NotBlank
    private final String airport;


    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }


    public String getCity() {
        return city;
    }


    public String getAirport() {
        return airport;
    }


    @Override
    public String toString() {
        return airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country) && city.equalsIgnoreCase(airport1.city) && airport.equalsIgnoreCase(airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
