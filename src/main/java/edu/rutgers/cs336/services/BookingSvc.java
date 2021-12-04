package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.rutgers.cs336.services.FlightSvc.Flight;
import edu.rutgers.cs336.services.UserSvc.User;

@Service
public class BookingSvc {
    public enum Status {RESERVED, WAITING};

    public enum Type {
        FIRST_CLASS, BUSINESS, ECONOMY;

        public float priceMultiplier() {
            return switch (this) {
                case FIRST_CLASS -> 3.0f;
                case BUSINESS -> 2.0f;
                case ECONOMY -> 1.0f;
            };
        }
    };

    public static record Booking(
        Integer id,
        Integer customer_id,
        Type type,
        Float fare,
        Status status,
        LocalDateTime created_on,
        LocalDateTime purchased_on
    ) implements Serializable {
        private static Booking mapper(ResultSet rs, int i) throws SQLException {
            return new Booking(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                Type.valueOf(rs.getString("type").toUpperCase()),
                rs.getFloat("fare"),
                Status.valueOf(rs.getString("status").toUpperCase()),
                rs.getTimestamp("created_on").toLocalDateTime(),
                rs.getTimestamp("purchased_on").toLocalDateTime());
        }
    }

    @Autowired
    private Database db;

    @Autowired
    private UserSvc users;

    public User getCustomer(Booking booking) {
        return users.findById(booking.customer_id()).orElseThrow();
    }

    public List<Flight> getFlights(Booking booking) {
        return db.index("SELECT * FROM flight WHERE id IN "
                      + "    (SELECT flight_id FROM booking_flight WHERE booking_id = ?)",
                        Flight::mapper,
                        booking.id());
    }

    public int getSeatNumber(Booking booking, Flight flight) {
        return db.count(
            "SELECT "
          + "  (SELECT COUNT(*) FROM booking_flight WHERE updated < "
          + "    (SELECT purchased_on FROM booking WHERE id = ?)) "
          + "  + 1",
            booking.id());
    }

    public List<User> getWaitingCustomers(Flight flight) {
        return db.index("SELECT * FROM user WHERE id IN "
                      + "    (SELECT customer_id FROM booking_flight "
                      + "        JOIN booking ON (booking_flight.booking_id = booking.id) "
                      + "        WHERE booking_flight.status = 'waiting' AND flight_id = ?)",
                        User::mapper,
                        flight.id());
    }

    public void addFlight(Booking booking, Flight flight, Status status) {
        db.insert("INSERT INTO booking_flight (booking_id, flight_id, status, updated) VALUES (?, ?, ?, ?)",
            booking.id(), flight.id(), status.toString().toLowerCase(), LocalDateTime.now());
    }

    public void reserve(Booking booking) {
        db.update("UPDATE booking SET status = 'reserved' WHERE id = ?", booking.id());
        db.update("UPDATE booking_flight SET status = 'reserved', updated = ? WHERE booking_id = ?",
            LocalDateTime.now(), booking.id());
    }

    public List<Booking> index() {
        return db.index("SELECT * FROM booking", Booking::mapper);
    }

    public List<Booking> indexByCustomer(User user) {
        return db.index("SELECT * FROM booking WHERE customer_id = ?", Booking::mapper, user.id());
    }

    public Optional<Booking> findById(int id) {
        return db.find("SELECT * FROM booking WHERE id = ?", Booking::mapper, id);
    }

    public Optional<Booking> findByTimestamp(LocalDateTime created) {
        return db.find("SELECT * FROM booking WHERE created_on = ?", Booking::mapper, created);
    }

    public void create(Booking b) {
        System.out.println(b);
        db.insert(
            "INSERT INTO booking (customer_id, type, fare, status, created_on, purchased_on) VALUES (?, ?, ?, ?, ?, ?)",
            b.customer_id(), b.type().toString().toLowerCase(), b.fare(), b.status().toString().toLowerCase(), b.created_on(), b.purchased_on());
    }

    public void deleteById(int id) {
        db.delete("DELETE FROM booking WHERE id = ?", id);
    }
}
