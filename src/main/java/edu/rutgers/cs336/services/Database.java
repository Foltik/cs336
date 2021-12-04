package edu.rutgers.cs336.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class Database {
    @Autowired
    private JdbcTemplate db;

    public <T> List<T> index(String sql, RowMapper<T> mapper, Object ...args) {
        return db.query(sql, mapper, args);
    }

    public <T> List<T> index(String sql, RowMapper<T> mapper) {
        return db.query(sql, mapper);
    }

    public <T> Optional<T> find(String sql, RowMapper<T> mapper, Object ...args) {
        var lst = index(sql, mapper, args);
        if (!lst.isEmpty()) {
            return Optional.of(lst.get(0));
        } else {
            return Optional.empty();
        }
    }

    public <T> Optional<T> find(String sql, RowMapper<T> mapper) {
        var lst = index(sql, mapper);
        if (!lst.isEmpty()) {
            return Optional.of(lst.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void insert(String sql, Object ...args) {
        db.update(sql, args);
    }

    public void update(String sql, Object ...args) {
        db.update(sql, args);
    }

    public void delete(String sql, Object ...args) {
        db.update(sql, args);
    }

    public int count(String sql, Object ...args) {
        return db.query(sql, (ResultSetExtractor<Integer>)rs -> {
            rs.next();
            return rs.getInt(1);
        }, args);
    }
}
