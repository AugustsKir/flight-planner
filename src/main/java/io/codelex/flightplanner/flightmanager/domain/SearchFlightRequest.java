package io.codelex.flightplanner.flightmanager.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class SearchFlightRequest {
    @NotBlank
    @NotNull
    private final String from;
    @NotBlank
    @NotNull
    private final String to;
    @NotBlank
    @NotNull
    private final String departureDate;

    public SearchFlightRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchFlightRequest that = (SearchFlightRequest) o;
        return from.equals(that.from) && to.equals(that.to) && departureDate.equals(that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, departureDate);
    }

    @Override
    public String toString() {
        return "SearchFlightRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }
}
