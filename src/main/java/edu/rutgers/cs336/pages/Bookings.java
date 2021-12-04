package edu.rutgers.cs336.pages;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.rutgers.cs336.pages.Search.Trip;
import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.BookingSvc;
import edu.rutgers.cs336.services.FlightSvc;
import edu.rutgers.cs336.services.BookingSvc.Type;
import edu.rutgers.cs336.services.BookingSvc.Booking;
import edu.rutgers.cs336.services.BookingSvc.Status;
import edu.rutgers.cs336.services.UserSvc.Role;
import edu.rutgers.cs336.services.UserSvc.User;

@Controller
@RequestMapping("/bookings")
public class Bookings {
    private static record BookingForm(Type type, String ids) {}

    @Autowired
    private BookingSvc bookings;

    @Autowired
    private AirportSvc airports;

    @Autowired
    private FlightSvc flights;

    @GetMapping
    public String index(HttpSession session, Model model) {
        var user = (User)session.getAttribute("user");
        if (user == null)
            return "login";
        else if (user.role() != Role.CUSTOMER)
            return "unauthorized";

        var books = bookings.indexByCustomer(user);
        model.addAttribute("bookings", books);

        // Get seat number for each flight for each booking
        var seats = books.stream()
            .collect(Collectors.toMap(
                b -> b.id(),
                b -> bookings.getFlights(b).stream()
                    .collect(Collectors.toMap(
                        f -> f.id(),
                        f -> bookings.getSeatNumber(b, f)))));
        model.addAttribute("seats", seats);

        // Pre compute Trip for each booking
        var trips = books.stream()
            .collect(Collectors.toMap(
                b -> b.id(),
                b -> new Trip(b.type(), bookings.getFlights(b))));
        model.addAttribute("trips", trips);

        // Check whether each booking is able to be reserved
        var available = books.stream()
            .collect(Collectors.toMap(
                b -> b.id(),
                b -> bookings.getFlights(b).stream()
                    .allMatch(f -> flights.getRemainingSeats(f) > 0)));
        model.addAttribute("available", available);

        // Get list of airports
        model.addAttribute("airports", airports.index().stream()
            .collect(Collectors.toMap(a -> a.id(), a -> a)));

        return "bookings";
    }

    @PostMapping
    public String create(@ModelAttribute BookingForm form, HttpSession session, Model model) {
        var user = (User)session.getAttribute("user");
        if (user == null || user.role() != Role.CUSTOMER)
            return "unauthorized";

        var trip = new Trip(form.type(), Arrays.asList(form.ids.split(",")).stream()
            .map(id -> flights.findById(Integer.parseInt(id)).orElseThrow())
            .collect(Collectors.toList()));

        var full = trip.flights().stream()
            .anyMatch(f -> flights.getRemainingSeats(f) == 0);

        var status = full ? Status.WAITING : Status.RESERVED;
        var created = LocalDateTime.now();
        var purchased = full ? null : created;

        var booking = new Booking(null, user.id(), form.type(), trip.price(), status, created, purchased);

        bookings.create(booking);
        booking = bookings.findByTimestamp(booking.created_on()).orElseThrow();
        for (var f : trip.flights()) {
            bookings.addFlight(booking, f, status);
        }

        model.addAttribute("message", "Trip booked!");

        return index(session, model);
    }

    @PutMapping
    public String reserve(@RequestParam Integer id, HttpSession session, Model model) {
        var booking = bookings.findById(id).orElseThrow();
        bookings.reserve(booking);

        model.addAttribute("message", "Booking reserved!");
        return index(session, model);
    }

    @DeleteMapping
    public String delete(@RequestParam Integer id, HttpSession session, Model model) {
        var booking = bookings.findById(id).orElseThrow();
        var fullFlights = bookings.getFlights(booking).stream()
            .filter(f -> flights.getRemainingSeats(f) == 0)
            .collect(Collectors.toList());

        var usersToNotify = fullFlights.stream()
            .flatMap(f -> bookings.getWaitingCustomers(f).stream())
            .distinct()
            .collect(Collectors.toList());

        for (var u : usersToNotify) {
            System.out.println("Notifying: " + u.first_name() + " " + u.last_name());
        }

        bookings.deleteById(booking.id());

        model.addAttribute("message", "Booking cancelled!");
        return index(session, model);
    }
}
