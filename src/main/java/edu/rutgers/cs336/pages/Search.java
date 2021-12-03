package edu.rutgers.cs336.pages;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.AirportSvc.Airport;
import edu.rutgers.cs336.services.FlightSvc.Flight;
import edu.rutgers.cs336.services.FlightSvc.Type;

@Controller
@RequestMapping("/search")
public class Search {
    private static int FLEX = 1;

    enum Sort {PRICE, TAKEOFF, LANDING, DURATION};
    enum Direction {ASCENDING, DESCENDING};

    public static record Query(
        Integer origin,
        Integer destination,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate departure,
        Boolean departure_flexible,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate arrival,
        Boolean arrival_flexible,

        Type type,

        Sort sort,
        Direction direction
    ) {
        public Boolean departure_flexible() {
            return departure_flexible == null ? false : departure_flexible;
        }

        public Boolean arrival_flexible() {
            return arrival_flexible == null ? false : arrival_flexible;
        }
    };

    public static record Trip(List<Flight> flights) {
        public float price() {
            return flights.stream()
                .map(Flight::fare)
                .reduce(0.0f, Float::sum);
        }

        public long duration() {
            return flights.stream()
                .map(f -> f.takeoff_time().until(f.landing_time(), ChronoUnit.MINUTES))
                .reduce(0L, Long::sum);
        }

        public LocalTime takeoff_time() {
            return flights.get(0).takeoff_time();
        }

        public LocalTime landing_time() {
            return flights.get(flights.size() - 1).landing_time();
        }
    }

    private boolean valid(LocalDate date, Set<DayOfWeek> days, boolean flexible) {
        if (flexible) {
            return days.stream().filter(day -> {
                var next = date.with(TemporalAdjusters.nextOrSame(day));
                var prev = date.with(TemporalAdjusters.previousOrSame(day));

                return ChronoUnit.DAYS.between(date, next) <= FLEX
                    || ChronoUnit.DAYS.between(prev, date) <= FLEX;
            }).findFirst().isPresent();
        } else {
            return days.contains(date.getDayOfWeek());
        }
    }

    private List<Trip> oneWayTrips(LocalDate date, boolean flexible, Airport origin, Airport destination) {
        // Map<Integer, Flight> flights = this.flights.index().stream()
        //     .collect(Collectors.toMap(f -> f.id(), f -> f));
        // Map<Integer, Airport> airports = this.airports.index().stream()
        //     .collect(Collectors.toMap(a -> a.id(), a -> a));

        // var origins = flights.values().stream()
        //     .filter(f -> valid(query.departure(), f.days(), query.departure_flexible()))
        //     .collect(Collectors.toList());

        // var trips = new ArrayList<Trip>();
        // for (var origin : origins) {
        //     var visited = new HashSet<Flight>();

        //     var fringe = new ArrayDeque<List<Flight>>();
        //     fringe.add(new ArrayList<>(List.of(origin)));

        //     while (!fringe.isEmpty()) {
        //         var path = fringe.pop();
        //         var flight = path.get(path.size() - 1);

        //         // Add to trips if we've completed the flight
        //         var valid = valid(query.arrival(), flight.days(), query.arrival_flexible());
        //         if (flight.to_airport_id() == query.destination && valid) {
        //             trips.add(new Trip(path));
        //             continue;
        //         } else {
        //             visited.add(flight);
        //         }

        //         // Add the next flight to the fringe if not visited
        //         var next = flights.get(flight.to_airport_id());
        //         if (!visited.contains(next)) {
        //             path.add(next);
        //             fringe.add(path);
        //         }
        //     }
        // }
        return null;
    }

    private List<Trip> roundTrips(LocalDate date, boolean flexible, Airport origin, Airport destination) {
        return flights.index().stream()
            .filter(f -> f.type().equals(Type.ROUND_TRIP) && valid(date, f.days(), flexible))
            .map(f -> new Trip(List.of(f)))
            .collect(Collectors.toList());
    }

    @Autowired
    private FlightSvc flights;
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index(Model model) {
        if (model.getAttribute("airports") == null)
            model.addAttribute("airports", airports.index());

        if (model.getAttribute("query") == null)
            model.addAttribute("query", new Query(
                null, null,
                LocalDate.now(), true,
                LocalDate.now(), true,
                Type.ONE_WAY,
                Sort.PRICE, Direction.ASCENDING));

        return "search";
    }

    @PostMapping
    public String search(@ModelAttribute Query query, Model model) {
        model.addAttribute("query", query);
        System.out.println(query);

        var origin = airports.findById(query.origin()).orElseThrow();
        var destination = airports.findById(query.destination()).orElseThrow();

        List<Trip> trips = switch (query.type()) {
            case ONE_WAY -> oneWayTrips(query.departure(), query.departure_flexible(), origin, destination);
            case ROUND_TRIP -> query.departure() == query.arrival()
                ? roundTrips(query.departure(), query.departure_flexible(), origin, destination)
                : null;
        };

        // Sorting
        var comparator = switch (query.sort()) {
            case PRICE -> Comparator.comparing(Trip::price);
            case DURATION -> Comparator.comparing(Trip::duration);
            case TAKEOFF -> Comparator.comparing(Trip::takeoff_time);
            case LANDING -> Comparator.comparing(Trip::landing_time);
        };
        switch (query.direction()) {
            case ASCENDING: break;
            case DESCENDING: comparator = comparator.reversed();
        }
        trips.sort(comparator);
        model.addAttribute("trips", trips);


        return index(model);
    }
}
