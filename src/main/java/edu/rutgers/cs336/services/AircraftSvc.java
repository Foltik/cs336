package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.rutgers.cs336.services.AirlineSvc.Airline;

@Service
public class AircraftSvc {
    public static record Aircraft(Integer id, Integer airline_id, String model, Integer seats) implements Serializable {
        private static Aircraft mapper(ResultSet rs, int i) throws SQLException {
            return new Aircraft(
                rs.getInt("id"),
                rs.getInt("airline_id"),
                rs.getString("model"),
                rs.getInt("seats"));
        }
    }

    @Autowired
    private Database db;

    @Autowired
    private AirlineSvc airlines;

    public Airline getAirline(Aircraft aircraft) {
        return airlines.findById(aircraft.airline_id()).orElseThrow();
    }

    public List<Aircraft> index() {
        return db.index("SELECT * FROM aircraft", Aircraft::mapper);
    }

    public Optional<Aircraft> findById(int id) {
        return db.find("SELECT * FROM aircraft WHERE id = ?", Aircraft::mapper, id);
    }

    public void update(Aircraft a){
        db.update("UPDATE aircraft SET model = ? WHERE id = ?", a.model, a.id);
    }

    public void create(Aircraft a){
        db.insert("INSERT INTO aircraft (model) VALUES (?)", a.model);
    }

    public void delete(int id){
        db.delete("DELETE FROM airline WHERE id = ?", id);
    }
}
