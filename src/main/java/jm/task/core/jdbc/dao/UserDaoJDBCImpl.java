package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import static jm.task.core.jdbc.dao.SQL.*;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private static Connection connection;
    static {
        connection = Util.getConnection();
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        transaction(CREATE_TABLE);
    }

    public void dropUsersTable() {
        transaction(DROP_TABLE);
    }

    public void saveUser(String name, String lastName, byte age) {
        transaction(SAVE_USER, ps -> {
            try {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                logger.info("User с именем – " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void removeUserById(long id) {
        transaction(REMOVE_USER, ps -> {
            try {
                ps.setLong(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = GET_ALL_USERS;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        transaction(CLEAN_USERS);
    }
    private void transaction (String sql) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void transaction (String sql, Consumer<PreparedStatement> prs) {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            prs.accept(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
}
