package io.codelex.flightplanner.flightmanager.domain;

import org.springframework.lang.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.concurrent.atomic.AtomicInteger;

public class Flight {
    private static final AtomicInteger nextID = new AtomicInteger(1);
    @Valid
    @NotNull
    private final Airport from;
    @Valid
    @NotNull
    private final Airport to;
    @NotBlank
    @NotNull
    private final String carrier;
    @NotBlank
    @NotNull
    private final String departureTime;
    @NotBlank
    @NotNull
    private final String arrivalTime;
    private final Integer id;

    public Flight(@NonNull Airport from, @NonNull Airport to, @NonNull String carrier, @NonNull String departureTime, @NonNull String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.id = nextID.getAndIncrement();
    }

    @NonNull
    public Airport getFrom() {
        return from;
    }

    @NonNull
    public Airport getTo() {
        return to;
    }

    @NonNull
    public String getDepartureTime() {
        return departureTime;
    }

    @NonNull
    public String getArrivalTime() {
        return arrivalTime;
    }

    @NonNull
    public String getCarrier() {
        return carrier;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return from.equals(flight.from) && to.equals(flight.to) && carrier.equals(flight.carrier) && departureTime.equals(flight.departureTime) && arrivalTime.equals(flight.arrivalTime);
    }

}
