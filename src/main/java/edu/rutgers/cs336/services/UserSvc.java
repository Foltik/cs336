package edu.rutgers.cs336.services;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSvc {
    enum Role {ADMIN, REPRESENTATIVE, CUSTOMER}

    public static record User(Integer id, String username, String password, String first_name, String last_name, Role role) implements Serializable {
        private static User mapper(ResultSet rs, int i) throws SQLException {
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                Role.valueOf(rs.getString("role").toUpperCase()));
        }
    }

    @Autowired
    private Database db;

    public List<User> index() {
        return db.index("SELECT * FROM user", User::mapper);
    }

    public Optional<User> findById(int id) {
        return db.find("SELECT * FROM user WHERE id = ?", User::mapper, id);
    }

    public Optional<User> findByUsername(String username) {
        return db.find("SELECT * FROM user WHERE username = ?", User::mapper, username);
    }

    public Optional<User> login(User user) {
        return db.find("SELECT * FROM user WHERE username = ? AND password = ?",
            User::mapper,
            user.username(),
            user.password());
    }

    public Optional<User> register(User user) {
        var existing = findByUsername(user.username());

        if (existing.isEmpty()) {
            db.insert("INSERT INTO user (username, password, first_name, last_name, role) VALUES (?, ?, ?, ?, ?)",
                user.username(),
                user.password(),
                user.first_name(),
                user.last_name(),
                Role.CUSTOMER.toString());

            return findByUsername(user.username());
        } else {
            return Optional.empty();
        }
    }

    // public void update(User a) {
    //     db.update("UPDATE user SET first_name = ?, last_name = ? WHERE id = ?", a.first_name, a.last_name, a.id);
    // }

    // public void delete(int id) {
    //     db.update("DELETE FROM user WHERE id = ?", id);
    // }
}
