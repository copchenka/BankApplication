package ru.levelup.bank.repository.jdbc;

import lombok.RequiredArgsConstructor;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.AccountJdbc;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.repository.AccountRepository;
import ru.levelup.bank.repository.AccountRepositoryJdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcAccountRepository implements AccountRepositoryJdbc {
    private final JdbcConnectionManager cm;

    @Override
    public void update(AccountJdbc account) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("update accounts set account_number = ?, " +
                    "open_datetime = ?, " +
                    "status = ?, " +
                    "type = ?, " +
                    "bd_id = ? " +
                    "where id = ?"
            );
            stmt.setString(1, account.getAccountNumber());
            stmt.setDate(2, account.getOpenDatetime());
            stmt.setString(3, account.getStatus());
            stmt.setString(4, account.getType());
            stmt.setLong(5, account.getBankDocumentId());
            stmt.setInt(6, account.getId());
            stmt.executeUpdate();//executeUpdate- вызывается для INSERT,DELETE,UPDATE +DDL команды (ALTER,)
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }


    @Override
    public AccountJdbc create(String accountNumber, Date openDatetime, String status, String type, Long bankDocumentId) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "insert into accounts (account_number, open_datetime, status, type, bd_id) values(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, accountNumber);
            stmt.setDate(2, openDatetime);
            stmt.setString(3, status);
            stmt.setString(4, type);
            stmt.setLong(5, bankDocumentId);
            int rowsAffected = stmt.executeUpdate();//executeUpdate- вызывается для INSERT,DELETE,UPDATE +DDL команды (ALTER,)
            System.out.println("Added rows: " + rowsAffected);
            //Получение сгенерированного id после create
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            Integer generatedAccountId = rs.getInt(1);
            return new AccountJdbc(
                    generatedAccountId,
                    accountNumber,
                    openDatetime,
                    status,
                    type,
                    bankDocumentId
            );
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public void remove(AccountJdbc account) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("delete from accounts where id = ?");
            stmt.setInt(1, account.getId());
            stmt.executeUpdate();
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public List<AccountJdbc> all() {
        try (Connection conn = cm.openConnection()) {
            Statement stmt = conn.createStatement();//statement- интерфейс через который можно исполнять sql запросы
            ResultSet rs = stmt.executeQuery("select * from accounts");//executeQuery позволяет исполнить select запросы
            List<AccountJdbc> allAccounts = new ArrayList<>();
            while (rs.next()) {
                allAccounts.add(map(rs));
            }
            return allAccounts;
        } catch (SQLException exc) {
            System.err.println("Error during loading accounts from database");
            System.err.println("SQL error" + exc.getSQLState());
            throw new RuntimeException(exc);
        }
    }

    private AccountJdbc map(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String accountNumber = rs.getString("account_number");
        Date openDatetime = rs.getDate("open_datetime");
        String status = rs.getString("status");
        String type = rs.getString("type");
        Long bankDocumentId = rs.getLong("bd_id");
        return new AccountJdbc(
                id,
                accountNumber,
                openDatetime,
                status,
                type,
                bankDocumentId
        );
    }
}
