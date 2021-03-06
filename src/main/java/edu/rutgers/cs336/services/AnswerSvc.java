package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerSvc {
    public static record Answer(Integer id, Integer representative_id, String body, Integer qid) implements Serializable {
        private static Answer mapper(ResultSet rs, int i) throws SQLException {
            return new Answer(
                rs.getInt("id"),
                rs.getInt("representative_id"),
                rs.getString("body"),
                rs.getInt("qid"));
        }
    }

    @Autowired
    private Database db;

    public List<Answer> index() {
        return db.index("SELECT * FROM answer", Answer::mapper);
    }

    public Optional<Answer> findById(int id) {
        return db.find("SELECT * FROM answer WHERE id = ?", Answer::mapper, id);
    }

    public void add(Answer answer) {
        db.insert("INSERT INTO answer VALUES (?, ?, ?, ?)", answer.id(), answer.representative_id(), answer.body(), answer.qid());
    }

    public List<Answer> findByKeyword(String keyword) {
        if (keyword == null || keyword.equals("")){
            List<Answer> a = new ArrayList<Answer>();
            return a;
        }
        String query = "SELECT * FROM answer WHERE body LIKE '%" + keyword + "%'";
        return db.index(query, Answer::mapper);
    }
}
