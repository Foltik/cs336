package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionSvc {
    public static record Question(Integer id, Integer customer_id, String title, String body) implements Serializable {
        private static Question mapper(ResultSet rs, int i) throws SQLException {
            return new Question(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getString("title"),
                rs.getString("body"));
        }
    }

    @Autowired
    private Database db;

    public List<Question> index() {
        return db.index("SELECT * FROM question", Question::mapper);
    }

    public Optional<Question> findById(int id) {
        return db.find("SELECT * FROM question WHERE id = ?", Question::mapper, id);
    }

    public void add(Question question) {
        db.insert("INSERT INTO question VALUES (?, ?, ?, ?)", question.id(), question.customer_id(), question.title(), question.body());
    }


}
