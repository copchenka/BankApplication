package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.AccountJdbc;

import java.sql.Date;
import java.util.List;

public interface AccountRepositoryJdbc {
    List<AccountJdbc> all();

    void update(AccountJdbc account);

    AccountJdbc create(
            String accountNumber,
            Date openDatetime,
            String status,
            String type,
            Long bankDocumentId
    );

    void remove(AccountJdbc account);

}
