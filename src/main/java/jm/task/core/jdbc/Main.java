package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import javax.sound.midi.Soundbank;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.createUsersTable();


        userDao.saveUser("Ivan", "Ivanov", (byte) 15);
        userDao.saveUser("Dmitry", "Dmitriev", (byte) 25);
        userDao.saveUser("Katya", "Katina", (byte) 45);
        userDao.saveUser("Alexey", "Alexseev", (byte) 35);


        List<User> users = userDao.getAllUsers();
        System.out.println("Список пользователей в нашей базе:");
        for (User user : users) {
            System.out.println(user);
            // реализуйте алгоритм здесь
        }
        userDao.cleanUsersTable();


        userDao.dropUsersTable();


    }
}
