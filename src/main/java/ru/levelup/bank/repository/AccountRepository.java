package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Account;

import java.sql.Date;
import java.util.List;

public interface AccountRepository {
    List<Account> all();

    void update(Account account);

   Account create(
            String accountNumber,
            Date openDatetime,
            String status,
            String type,
            Long bankDocumentId
    );

    void remove(Account account);

}
