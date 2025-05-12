package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.ConfigUtil;


import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        UserService userDao;
        String type = ConfigUtil.getType();
        switch (type.toLowerCase()) {
            case "hibernate":
                userDao = new UserServiceImpl(new UserDaoHibernateImpl());
                logger.info("Подключение через Hibernate");
                break;
            case "jdbc":
                userDao = new UserServiceImpl(new UserDaoJDBCImpl());
                logger.info("Подключение через JDBC");
                break;
            default:
                logger .severe("Неизвестный тип подключения " + type);
                throw new RuntimeException();
        }

        userDao.createUsersTable();

        userDao.saveUser("Ivan", "Ivanov", (byte) 15);
        userDao.saveUser("Dmitry", "Dmitriev", (byte) 25);
        userDao.saveUser("Katya", "Katina", (byte) 45);
        userDao.saveUser("Alexey", "Alexseev", (byte) 35);

        List<User> users = userDao.getAllUsers();
        logger.info ("Список пользователей в нашей базе:");
        for (User user : users) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();

        userDao.dropUsersTable();

    }
}
