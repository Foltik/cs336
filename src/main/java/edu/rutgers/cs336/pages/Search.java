package edu.rutgers.cs336.pages;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import edu.rutgers.cs336.services.BookingSvc.Type;

@Controller
@RequestMapping("/search")
public class Search {
    private static int FLEX = 1;

    public enum Direction {ONE_WAY, ROUND_TRIP};
    enum Sort {PRICE, DURATION, STOPS, TAKEOFF, LANDING};
    enum Order {ASCENDING, DESCENDING};

    enum Filter {
        NONE, GREATER, LESS;

        public <T extends Comparable<T>> boolean apply(T a, T b) {
            return switch (this) {
                case NONE -> true;
                case GREATER -> a.compareTo(b) < 0;
                case LESS -> a.compareTo(b) > 0;
            };
        }
    }

    public static record Query(
        Integer origin,
        Integer destination,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate departure,
        Boolean departure_flexible,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate arrival,
        Boolean arrival_flexible,

        Direction direction,

        Type type,

        Sort sort,
        Order order,

        Float price,
        Filter price_filter,

        Long duration,
        Filter duration_filter,

        Long stops,
        Filter stops_filter,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime takeoff,
        Filter takeoff_filter,

        @DateTimeFormat(pattern = "HH:mm")
        LocalTime landing,
        Filter landing_filter
    ) {
        public Boolean departure_flexible() {
            return departure_flexible == null ? false : departure_flexible;
        }

        public Boolean arrival_flexible() {
            return arrival_flexible == null ? false : arrival_flexible;
        }
    };

    public static record Trip(Type type, List<Flight> flights) {
        public float price() {
            return type.priceMultiplier() * flights.stream()
                .map(Flight::fare)
                .reduce(0.0f, Float::sum);
        }

        public long duration() {
            return takeoff_time().until(landing_time(), ChronoUnit.MINUTES);
        }

        public LocalTime takeoff_time() {
            return flights.get(0).takeoff_time();
        }

        public LocalTime landing_time() {
            return flights.get(flights.size() - 1).landing_time();
        }

        public long stops() {
            return flights.size() - 1;
        }
    }

    private Set<DayOfWeek> flexDays(LocalDate date, boolean flexible) {
        var delta = flexible ? FLEX : 0;

        var days = new HashSet<DayOfWeek>();
        days.add(date.getDayOfWeek());
        for (int i = 1; i <= delta; i++) {
            days.add(date.plusDays(i).getDayOfWeek());
            days.add(date.minusDays(i).getDayOfWeek());
        }
        return days;
    }

    // Find a sequence of one-way flights from origin to destination with breadth first search
    private List<Trip> oneWayTrips(Type type, LocalDate date, boolean flexible, Airport origin, Airport destination) {
        var flights = this.flights.index();

        var trips = new ArrayList<Trip>();
        for (var day : flexDays(date, flexible)) {
            var origins = flights.stream()
                .filter(f -> f.days().contains(day))
                .filter(f -> f.from_airport_id() == origin.id())
                .collect(Collectors.toList());

            for (var o : origins) {
                var visited = new HashSet<Flight>();

                var fringe = new ArrayDeque<List<Flight>>();
                fringe.add(new ArrayList<>(List.of(o)));

                while (!fringe.isEmpty()) {
                    var path = fringe.pop();
                    var flight = path.get(path.size() - 1);

                    if (flight.to_airport_id() == destination.id()) {
                        // Add to trips if we've completed the flight
                        trips.add(new Trip(type, path));
                    } else {
                        // Otherwise, explore all adjacent flights
                        flights.stream()
                            .filter(f -> f.days().contains(day))
                            .filter(f -> f.from_airport_id() == flight.to_airport_id())
                            .filter(f -> f.takeoff_time().isAfter(flight.landing_time()))
                            .filter(f -> !visited.contains(f))
                            .forEach(f -> {
                                visited.add(f);
                                var extended = new ArrayList<>(path);
                                extended.add(f);
                                fringe.add(extended);
                            });
                    }
                }
            }
        }

        return trips;
    }

    private <T extends Comparable<T>> List<Trip> filter(List<Trip> trips, Function<Trip, T> accessor, Filter filter, T val) {
        if (val == null) return trips;
        return trips.stream()
            .filter(trip -> filter.apply(val, accessor.apply(trip)))
            .collect(Collectors.toList());
    }

    private List<Trip> filter(List<Trip> trips, Query q) {
        trips = filter(trips, t -> t.price(), q.price_filter(), q.price());
        trips = filter(trips, t -> t.duration(), q.duration_filter(), q.duration());
        trips = filter(trips, t -> t.stops(), q.stops_filter(), q.stops());
        trips = filter(trips, t -> t.takeoff_time(), q.takeoff_filter(), q.takeoff());
        trips = filter(trips, t -> t.landing_time(), q.landing_filter(), q.landing());
        return trips;
    }

    @Autowired
    private FlightSvc flights;
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index(Model model) {
        if (model.getAttribute("airports") == null)
            model.addAttribute("airports", airports.index().stream()
                .collect(Collectors.toMap(a -> a.id(), a -> a)));

        if (model.getAttribute("query") == null)
            model.addAttribute("query", new Query(
                null, null,
                LocalDate.now(), true,
                LocalDate.now(), true,
                Direction.ONE_WAY,
                Type.ECONOMY,
                Sort.PRICE, Order.ASCENDING,
                null, Filter.NONE,
                null, Filter.NONE,
                null, Filter.NONE,
                null, Filter.NONE,
                null, Filter.NONE));

        return "search";
    }

    @PostMapping
    public String search(@ModelAttribute Query query, Model model) {
        model.addAttribute("query", query);
        System.out.println(query);

        var origin = airports.findById(query.origin()).orElseThrow();
        var destination = airports.findById(query.destination()).orElseThrow();

        // Find trips
        var departing = oneWayTrips(query.type(), query.departure(), query.departure_flexible(), origin, destination);
        var arriving = oneWayTrips(query.type(), query.arrival(), query.arrival_flexible(), destination, origin);

        // Filter
        departing = filter(departing, query);
        arriving = filter(arriving, query);

        // Sort
        var comparator = switch (query.sort()) {
            case PRICE -> Comparator.comparing(Trip::price);
            case DURATION -> Comparator.comparing(Trip::duration);
            case STOPS -> Comparator.comparing(Trip::stops);
            case TAKEOFF -> Comparator.comparing(Trip::takeoff_time);
            case LANDING -> Comparator.comparing(Trip::landing_time);
        };
        switch (query.order()) {
            case ASCENDING: break;
            case DESCENDING: comparator = comparator.reversed();
        }
        departing.sort(comparator);
        arriving.sort(comparator);
        model.addAttribute("departing", departing);
        model.addAttribute("arriving", arriving);

        // Get remaining seats for each flight
        var seats = Stream.of(departing, arriving)
            .flatMap(trips -> trips.stream())
            .flatMap(trip -> trip.flights.stream())
            .distinct()
            .collect(Collectors.toMap(
                f -> f.id(),
                f -> flights.getRemainingSeats(f)));
        model.addAttribute("seats", seats);

        // Pre compute full status for trips
        var departingFull = departing.stream()
            .map(trip -> trip.flights().stream().anyMatch(f -> seats.get(f.id()) == 0))
            .collect(Collectors.toList());
        var arrivingFull = arriving.stream()
            .map(trip -> trip.flights().stream().anyMatch(f -> seats.get(f.id()) == 0))
            .collect(Collectors.toList());
        model.addAttribute("departingFull", departingFull);
        model.addAttribute("arrivingFull", arrivingFull);

        model.addAttribute("ids", (Function<Trip, String>)(Trip trip) -> trip.flights().stream()
            .map(f -> f.id().toString())
            .collect(Collectors.joining(",")));

        return index(model);
    }
}
