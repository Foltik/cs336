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
        LocalDateTime created_on,
        LocalDateTime purchased_on,
        Type type,
        Float fare
    ) implements Serializable {
        private static Booking mapper(ResultSet rs, int i) throws SQLException {
            return new Booking(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getTimestamp("takeoff_time").toLocalDateTime(),
                rs.getTimestamp("landing_time").toLocalDateTime(),
                Type.valueOf(rs.getString("type").toUpperCase()),
                rs.getFloat("fare"));
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
