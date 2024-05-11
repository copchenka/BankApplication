package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Account;

import java.sql.Date;
import java.util.List;

public interface AccountRepository<T> {
    List<T> all();

    void update(T account);

    T create(
            String accountNumber,
            Date openDatetime,
            String status,
            String type,
            Long bankDocumentId
    );

    void remove(T account);

}
