package io.codelex.flightplanner.flightmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "FLIGHTS")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial", name = "id", updatable = false, nullable = false)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "airport_from")
    @Valid
    private Airport from;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "airport_to")
    @Valid
    private Airport to;
    @NotBlank
    @JsonProperty("carrier")
    @Column(name = "airline")
    private String carrier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "departure_time")
    @JsonProperty("departureTime")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "arrival_time")
    @JsonProperty("arrivalTime")
    private LocalDateTime arrivalTime;

    protected Flight() {
    }

    public Flight(Long id, Airport from, Airport to, String carrier, LocalDateTime departureTimeText, LocalDateTime arrivalTimeText) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTimeText;
        this.arrivalTime = arrivalTimeText;
    }

    public String getCarrier() {
        return carrier;
    }

    @NotNull
    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    @NotNull
    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    @NotNull
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    @NotNull
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return from.equals(flight.from) && to.equals(flight.to) && carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

}
