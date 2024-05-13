package ru.levelup.bank.repository.hbm;

import org.hibernate.SessionFactory;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.repository.AccountRepository;

import java.sql.Date;
import java.util.List;

public class HibernateAccountRepository extends AbstractRepository implements AccountRepository {
    public HibernateAccountRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<Account> all() {
        return withSession(session -> session.createQuery("from Account", Account.class)
                .list());
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public Account create(String accountNumber, Date openDatetime, String status, String type, Long bankDocumentId) {
        return null;
    }

    @Override
    public void remove(Account account) {

    }

}
