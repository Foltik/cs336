package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportSvc {
    public static record TopFlights(Integer flight_id, Integer count) implements Serializable {
        private static TopFlights mapper(ResultSet rs, int i) throws SQLException {
            return new TopFlights(
                rs.getInt("flight_id"),
                rs.getInt("count"));
        }
    }

    public List<TopFlights> index() {
        return db.index("select flight_id, count(status) count from booking_flight where status='reserved' group by flight_id order by count desc LIMIT 5", TopFlights::mapper);
    }

    @Autowired
    private Database db;

}
