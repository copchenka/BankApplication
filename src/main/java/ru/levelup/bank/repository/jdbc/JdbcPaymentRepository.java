package ru.levelup.bank.repository.jdbc;

import lombok.RequiredArgsConstructor;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.Payment;
import ru.levelup.bank.domain.PaymentStatus;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.repository.PaymentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JdbcPaymentRepository implements PaymentRepository {
    private final JdbcConnectionManager cm;

    @Override
    public void changeStatus(int paymentId, PaymentStatus status) {

    }

    @Override
    public void changeStatus(int paymentId, String status) {

    }

    @Override
    public List<Payment> all() {
        try (Connection conn = cm.openConnection()) {
            Statement stmt = conn.createStatement();
            List<Payment> allPayments = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("select * from payments");
            while (rs.next()) {
                allPayments.add(map(rs));
            }
            return allPayments;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public Payment createPayment(Date datetime, Long amount, Integer accountFrom, Integer accountTo, PaymentStatus status) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "insert into payments (date, amount, account_from, account_to, status) values(?, ?, ?, ?, ?::payment_status)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setDate(1, datetime);
            stmt.setLong(2, amount);
            stmt.setInt(3, accountFrom);
            stmt.setInt(4, accountTo);

            stmt.setString(5,status.toString());
            stmt.executeUpdate();//executeUpdate- вызывается для INSERT,DELETE,UPDATE +DDL команды (ALTER,)
            //Получение сгенерированного id после create
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int generatedPaymentId = rs.getInt(1);
            return new Payment(
                    generatedPaymentId,
                    datetime,
                    amount,
                    accountFrom,
                    accountTo,
                    status
            );
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    public List<Payment> byAccountId(Account account) {
        try (Connection conn = cm.openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select * from payments where account_from = ? or account_to = ?");
            stmt.setInt(1, account.getId());
            stmt.setInt(2, account.getId());
            List<Payment> payments = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Payment p = map(rs);
                payments.add(p);
            }
            return payments;
        } catch (SQLException exc) {
            throw new RuntimeException(exc);
        }
    }

    private Payment map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Date datetime = rs.getDate("date");
        Long amount = rs.getLong("amount");
        Integer accountFrom = rs.getInt("account_from");
        Integer accountTo = rs.getInt("account_to");
        PaymentStatus status = PaymentStatus.valueOf(rs.getString("status"));
        return new Payment(id, datetime, amount, accountFrom, accountTo, status);
    }
}
