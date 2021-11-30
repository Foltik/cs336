package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.rutgers.cs336.services.AircraftSvc.Aircraft;
import edu.rutgers.cs336.services.AirportSvc.Airport;

@Service
public class FlightSvc {
    enum Type {DOMESTIC, INTERNATIONAL};
    enum Direction {ONE_WAY, ROUND_TRIP};
    enum Day {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};

    public static record Flight(
        Integer id, 
        Integer aircraft_id, 
        Integer from_airport_id, 
        Integer to_airport_id, 
        LocalTime takeoff_time,
        LocalTime landing_time,
        Type type,
        Direction direction
    ) implements Serializable {
        private static Flight mapper(ResultSet rs, int i) throws SQLException {
            return new Flight(
                rs.getInt("id"),
                rs.getInt("aircraft_id"),
                rs.getInt("from_airport_id"),
                rs.getInt("to_aircraft_id"),
                rs.getTimestamp("takeoff_time").toLocalDateTime().toLocalTime(),
                rs.getTimestamp("landing_time").toLocalDateTime().toLocalTime(),
                Type.valueOf(rs.getString("type").toUpperCase()),
                Direction.valueOf(rs.getString("direction").toUpperCase()));
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

    public List<Flight> index() {
        return db.index("SELECT * FROM flight", Flight::mapper);
    }

    public Optional<Flight> findById(int id) {
        return db.find("SELECT * FROM flight WHERE id = ?", Flight::mapper, id);
    }
}
