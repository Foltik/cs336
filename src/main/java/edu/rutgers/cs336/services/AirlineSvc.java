package edu.rutgers.cs336.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineSvc {
    public static record Airline(Integer id, String name) {
        public static Airline mapper(ResultSet rs, int i) throws SQLException {
            return new Airline(rs.getInt("id"), rs.getString("name"));
        }
    }

    @Autowired
    private Database db;

    public List<Airline> index() {
        return db.index("SELECT * FROM airline", Airline::mapper);
    }

    public Optional<Airline> findById(int id) {
        return db.find("SELECT * FROM airline WHERE id = ?", Airline::mapper, id);
    }

    public void create(Airline a) {
        db.insert("INSERT INTO airline (name) VALUES (?)", a.name);
    }

    public void update(Airline a) {
        db.update("UPDATE airline SET name = ? WHERE id = ?", a.name, a.id);
    }

    public void delete(int id) {
        db.delete("DELETE FROM airline WHERE id = ?", id);
    }
}
