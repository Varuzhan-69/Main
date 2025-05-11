package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final SessionFactory sessionFactory;
    private static final Properties jdbcProperties = loadJdbcProperties();;

    static{


        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found", e);
        }
        try{
            Configuration configuration = new Configuration();
            configuration.addProperties(loadHiberProperties());
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadHiberProperties(){
        Properties prop = new Properties();
        try(InputStream input = Util.class.getClassLoader().getResourceAsStream("hibernate.properties")){
            prop.load(input);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    jdbcProperties.getProperty("jdbc.url"),
                    jdbcProperties.getProperty("jdbc.user"),
                    jdbcProperties.getProperty("jdbc.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static Properties loadJdbcProperties(){
        Properties prop = new Properties();
        try(InputStream input = Util.class.getClassLoader().getResourceAsStream("jdbc.properties")){
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    // реализуйте настройку соеденения с БД
}
