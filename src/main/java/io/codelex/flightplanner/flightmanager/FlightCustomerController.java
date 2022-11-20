package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Airport;
import io.codelex.flightplanner.flightmanager.domain.Flight;
import io.codelex.flightplanner.flightmanager.domain.PageResult;
import io.codelex.flightplanner.flightmanager.domain.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightCustomerController {
    private final FlightService flightService;

    public FlightCustomerController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadInput() {
        return "Bad input, make sure you entered everything correctly!";
    }

    @GetMapping("/airports")
    private List<Airport> findAirport(@RequestParam String search) {
        return flightService.findAirportByInput(search);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable Integer id) {
        return flightService.findFlightsByID(id);
    }

    @PostMapping("/flights/search")
    private PageResult searchFlights(@Valid @RequestBody SearchFlightRequest req) {
        return flightService.searchFlights(req);

    }
}
