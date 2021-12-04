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

    @Autowired
    private Database db;

    public List<GenericResult> TopFlights() {
        return db.index("select flight_id id, count(status) count from booking_flight where status='reserved' group by flight_id order by count desc LIMIT 5", GenericResult::mapper);
    }

    public Optional<GenericResult> TopCustomer() {
        return db.find("SELECT * FROM (select user.id, sum(fare) count from user, booking where user.id=booking.customer_id GROUP BY user.id order by count desc LIMIT 1) as a", GenericResult::mapper);
    }

    public static record SalesResult(Integer m, Integer fare) implements Serializable {
        private static SalesResult mapper(ResultSet rs, int i) throws SQLException {
            return new SalesResult(
                rs.getInt("m"),
                rs.getInt("fare"));
        }
    }

    public List<SalesResult> GetSales(){
        return db.index("select month(purchased_on) m, sum(fare) fare from booking group by m", SalesResult::mapper);
    }

    public static record ReportReservations(Integer customer_id, Integer fare, String purchased_on) implements Serializable {
        private static ReportReservations mapper(ResultSet rs, int i) throws SQLException {
            return new ReportReservations(
                rs.getInt("customer_id"),
                rs.getInt("fare"),
                rs.getString("purchased_on"));
        }
    }

    public List<ReportReservations> GetReservationsByID(int id)
    {
        return db.index("select customer_id, fare, purchased_on from booking as b, booking_flight as bf WHERE bf.flight_id=? AND bf.booking_id=b.id", ReportReservations::mapper,id);
    }

    

}
