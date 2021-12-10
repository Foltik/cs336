package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.rutgers.cs336.services.AircraftSvc.Aircraft;
import edu.rutgers.cs336.services.AirportSvc.Airport;

@Service
public class FlightSvc {
    public enum Domain {DOMESTIC, INTERNATIONAL};
    public enum Day {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};

    public static record Flight(
        Integer id,
        Integer aircraft_id,
        Integer from_airport_id,
        Integer to_airport_id,
        LocalTime takeoff_time,
        LocalTime landing_time,
        Set<DayOfWeek> days,
        Domain domain,
        Float fare
    ) implements Serializable {
        public static Flight mapper(ResultSet rs, int i) throws SQLException {
            return new Flight(
                rs.getInt("id"),
                rs.getInt("aircraft_id"),
                rs.getInt("from_airport_id"),
                rs.getInt("to_airport_id"),
                rs.getTimestamp("takeoff_time").toLocalDateTime().toLocalTime(),
                rs.getTimestamp("landing_time").toLocalDateTime().toLocalTime(),
                Arrays.asList(rs.getString("days").split(",")).stream().map(DayOfWeek::valueOf).collect(Collectors.toSet()),
                Domain.valueOf(rs.getString("domain").toUpperCase()),
                rs.getFloat("fare"));
        }
    }

    @Autowired
    private Database db;

    @Autowired
    private AircraftSvc aircrafts;

    @Autowired
    private AirportSvc airports;


    public Aircraft getAircraft(Flight flight) {
        return aircrafts.findById(flight.aircraft_id()).orElseThrow();
    }

    public Airport getToAirport(Flight flight) {
        return airports.findById(flight.to_airport_id()).orElseThrow();
    }

    public Airport getFromAirport(Flight flight) {
        return airports.findById(flight.from_airport_id()).orElseThrow();
    }

    public int getRemainingSeats(Flight flight) {
        return db.count(
            "SELECT "
          + "(SELECT seats FROM aircraft WHERE id IN (SELECT aircraft_id FROM flight WHERE id = ?))"
          + " - "
          + "(SELECT COUNT(*) FROM booking JOIN booking_flight ON (booking_flight.booking_id = booking.id)"
          + "    WHERE booking_flight.status = 'reserved' AND flight_id = ?)",
          flight.id(), flight.id());
    }

    public List<Flight> index() {
        return db.index("SELECT * FROM flight", Flight::mapper);
    }

    public Optional<Flight> findById(int id) {
        return db.find("SELECT * FROM flight WHERE id = ?", Flight::mapper, id);
    }

    public void delete(int id){
        db.delete("DELETE FROM flight WHERE id = ?", id);
    }

    public Optional<Flight> create(Flight f) {
        String days = String.join(",", f.days().stream().map(DayOfWeek::toString).collect(Collectors.toList()));
        var existing = findById(f.id);

        if(existing.isEmpty()) {
            db.insert("INSERT INTO flight (aircraft_id, from_airport_id, to_airport_id, takeoff_time, landing_time, days, domain, fare) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
            f.aircraft_id, f.from_airport_id, f.to_airport_id, f.takeoff_time, f.landing_time, f.days, f.domain, f.fare);
            return findById(f.id);
        }else{
                return Optional.empty();
            }
        }

    public void update(Flight f){
        db.update("UPDATE flight SET aircraft_id = ?, from_airport_id = ?, to_airport_id = ?, takeoff_time = ?, landing_time = ?, days = ?, domaine = ?, fare = ? WHERE id = ?", 
        f.aircraft_id, f.from_airport_id, f.to_airport_id, f.takeoff_time, f.landing_time, f.days, f.domain, f.fare);
    }

}
