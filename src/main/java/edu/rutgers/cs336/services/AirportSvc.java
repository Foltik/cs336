package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportSvc {
    public static record Airport(Integer id, String name) implements Serializable {
        private static Airport mapper(ResultSet rs, int i) throws SQLException {
            return new Airport(
                rs.getInt("id"),
                rs.getString("name"));
        }
    }

    @Autowired
    private Database db;

    public List<Airport> index() {
        return db.index("SELECT * FROM airport", Airport::mapper);
    }

    public Optional<Airport> findById(int id) {
        return db.find("SELECT * FROM airport WHERE id = ?", Airport::mapper, id);
    }

    public void update(Airport a){
        db.update("UPDATE airport SET name = ? WHERE id = ?", a.name, a.id);
    }
    public void create(Airport a){
        db.insert("INSERT INTO airport (name) VALUES (?)", a.name);
    }

    public void delete(int id){
        db.delete("DELETE FROM airport WHERE id = ?", id);
    }

}
