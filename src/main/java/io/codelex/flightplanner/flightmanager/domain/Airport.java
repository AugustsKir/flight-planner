package io.codelex.flightplanner.flightmanager.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "AIRPORTS")
public class Airport {
    @NotEmpty
    @JsonProperty("country")
    private String country;
    @NotEmpty
    @JsonProperty("city")
    private String city;
    @NotEmpty
    @Id
    @JsonProperty("airport")
    @Column(name = "airport_id")
    private String airport;


    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public Airport() {
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
