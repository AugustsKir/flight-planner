package io.codelex.flightplanner.flightmanager;

import io.codelex.flightplanner.flightmanager.domain.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin-api")
public class FlightAdminController {
    private final FlightService flightService;

    public FlightAdminController(FlightService flightService) {
        this.flightService = flightService;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadInput() {

        return "Bad input, make sure you entered everything correctly!";
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    private Flight addFlight(@Valid @RequestBody Flight req) {
        if (!flightService.isValidAirports(req)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!flightService.isNotRepeated(req)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (!flightService.isDateValid(req)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            flightService.addFlight(req);
            return req;
        }
    }

    @GetMapping("flights/available")
    public List<Flight> getList() {

        return flightService.flightList();
    }

    @DeleteMapping("/flights/{id}")
    public void deleteFlight(@PathVariable String id) {

        this.flightService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable String id) {
        return this.flightService.findFlightsByID(id);
    }

}