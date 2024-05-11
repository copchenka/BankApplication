package ru.levelup.bank.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionManager {
    //отвечает за подлкючение к бд- получение объекта Connection
    //JDBC-java database connectivity
    //drivers
    public Connection openConnection() throws SQLException{
        //url подключение к бд:
        // jdbc:<vendor_name>://<host>:<port>/<db_name>
        return DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/lk-bank",
                "postgres",
                "1234"
        );//устанавливает новое подключение из программы к БД

    }
}
