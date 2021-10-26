package edu.rutgers.cs336.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AirlineSvc {
    public static record Airline(Integer id, String name) {}

    @Autowired
    private JdbcTemplate db;

    public List<Airline> index() {
        return db.query("SELECT * FROM airline", (rs, i) -> new Airline(
            rs.getInt("id"), 
            rs.getString("name")));
    }

    public void create(Airline a) {
        db.update("INSERT INTO airline (name) VALUES (?)", a.name);
    }

    public void update(Airline a) {
        db.update("UPDATE airline SET name = ? WHERE id = ?", a.name, a.id);
    }

    public void delete(int id) {
        db.update("DELETE FROM airline WHERE id = ?", id);
    }
}
