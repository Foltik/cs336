package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.rutgers.cs336.services.UserSvc.User;

@Service
public class BookingSvc {
    enum Type {FIRST_CLASS, BUSINESS, ECONOMY};

    public static record Booking(
        Integer id, 
        Integer customer_id, 
        Float fare,
        LocalDateTime created_on,
        LocalDateTime purchased_on,
        Type type
    ) implements Serializable {
        private static Booking mapper(ResultSet rs, int i) throws SQLException {
            return new Booking(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getFloat("fare"),
                rs.getTimestamp("takeoff_time").toLocalDateTime(),
                rs.getTimestamp("landing_time").toLocalDateTime(),
                Type.valueOf(rs.getString("type").toUpperCase()));
        }
    }

    @Autowired
    private Database db;

    @Autowired
    private UserSvc users;

    public User getCustomer(Booking booking) {
        return users.findById(booking.customer_id()).orElseThrow();
    }

    public List<Booking> index() {
        return db.index("SELECT * FROM booking", Booking::mapper);
    }

    public Optional<Booking> findById(int id) {
        return db.find("SELECT * FROM booking WHERE id = ?", Booking::mapper, id);
    }
}
