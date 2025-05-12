package jm.task.core.jdbc.dao;

public class SQL {
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY" +
            ", name varchar NOT NULL, lastName varchar NOT NULL, age int check (age>0))" ;
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    public static final String CLEAN_USERS = "TRUNCATE TABLE users";
    public static final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    public static final String REMOVE_USER = "DELETE FROM users WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM users";

}
