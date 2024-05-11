package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.PaymentStatus;
import ru.levelup.bank.domain.Payment;

import java.sql.Date;
import java.util.List;

public interface PaymentRepository {
    void changeStatus(int paymentId, PaymentStatus status);

    void changeStatus(int paymentId, String status);

    List<Payment> all();

    Payment createPayment(
            Date datetime,
            Long amount,
            Integer accountFrom,
            Integer accountTo,
            PaymentStatus status
    );

    List<Payment> byAccountId(Account account);

}
