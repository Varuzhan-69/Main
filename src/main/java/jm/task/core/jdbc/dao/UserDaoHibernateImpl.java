package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        krasivyVyvod(session -> session.createNativeQuery(SQL.CREATE_TABLE).executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        krasivyVyvod(session -> session.createNativeQuery(SQL.DROP_TABLE).executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        krasivyVyvod(session -> session.save(new User(name, lastName, age)));
            logger.info("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        krasivyVyvod(session -> session.remove(session.get(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        krasivyVyvod(session -> session.createNativeQuery(SQL.CLEAN_USERS).executeUpdate());
    }

    public void krasivyVyvod(Consumer<Session> query) {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            query.accept(session);
            transaction.commit();
        }
    }
}


