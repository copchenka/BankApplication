package ru.levelup.bank.repository.jdbc;

import lombok.RequiredArgsConstructor;
import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.repository.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//@AllArgsConstructor-  со всеми полями
@RequiredArgsConstructor//конструктор с final  полями

public class JdbcCustomerRepository implements CustomerRepository {
    private final JdbcConnectionManager cm;

    @Override
    public List<Customer> all() {
        try (Connection conn = cm.openConnection()) {
            Statement stmt = conn.createStatement();//statement- интерфейс через который можно исполнять sql запросы
            ResultSet rs = stmt.executeQuery("select * from customers");//executeQuery позволяет исполнить select запросы
            List<Customer> allCustomers = new ArrayList<>();
            while (rs.next()) {
                allCustomers.add(map(rs));
            }
            return allCustomers;
        } catch (SQLException exc) {
            System.err.println("Error during loading customers from database");
            System.err.println("SQL error" + exc.getSQLState());
            throw new RuntimeException(exc);
        }
    }

    @Override
    public Customer create(long bdId, String firstName, String lastName, Date birthday, String passportSeria, String passportNumber) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "insert into customers (bank_document_id, first_name, last_name, birthday, passport_seria, passport_number) values(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setLong(1, bdId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setDate(4, birthday);
            stmt.setString(5, passportSeria);
            stmt.setString(6, passportNumber);
            int rowsAffected = stmt.executeUpdate();//executeUpdate- вызывается для INSERT,DELETE,UPDATE +DDL команды (ALTER,)
            System.out.println("Added rows: " + rowsAffected);
            //Получение сгенерированного id после create
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int generatedCustomerId = rs.getInt(1);
            return new Customer(
                    generatedCustomerId,
                    bdId,
                    firstName,
                    lastName,
                    birthday,
                    passportSeria,
                    passportNumber
            );
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public Customer byId(Integer customerId) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from customers where id = ?");
            stmt.setInt(1, customerId);//в первый ? закинет customerId
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? map(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        long bankDocumentId = rs.getLong("bank_document_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date birthday = rs.getDate("birthday");
        String passportSeria = rs.getString("passport_seria");
        String passportNumber = rs.getString("passport_number");
        return new Customer(
                id,
                bankDocumentId,
                firstName,
                lastName,
                birthday,
                passportSeria,
                passportNumber
        );
    }
}
