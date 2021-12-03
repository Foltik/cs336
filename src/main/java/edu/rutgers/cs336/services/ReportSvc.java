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
    public static record GenericResult(Integer id, Integer count) implements Serializable {
        private static GenericResult mapper(ResultSet rs, int i) throws SQLException {
            return new GenericResult(
                rs.getInt("id"),
                rs.getInt("count"));
        }
    }

    public List<GenericResult> TopFlights() {
        return db.index("select flight_id id, count(status) count from booking_flight where status='reserved' group by flight_id order by count desc LIMIT 5", GenericResult::mapper);
    }

    public Optional<GenericResult> TopCustomer() {
        return db.find("SELECT * FROM (select user.id, sum(fare) count from user, booking where user.id=booking.customer_id GROUP BY user.id order by count desc LIMIT 1) as a", GenericResult::mapper);
    }

    public List<GenericResult> GetSales(){
        return db.index("select * from (select month(purchased_on) id, fare count from booking) a group by a.id", GenericResult::mapper);
    }

    @Autowired
    private Database db;

}
