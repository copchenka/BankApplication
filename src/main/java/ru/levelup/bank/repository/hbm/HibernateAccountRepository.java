package ru.levelup.bank.repository.hbm;

import org.hibernate.SessionFactory;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.Customer;
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
        return withSession(session -> {
            Account newAccount = new Account(null, accountNumber, openDatetime, status, type);
            Customer customer = session.get(Customer.class, bankDocumentId);
            newAccount.setCustomer(customer);
            session.save(newAccount);
            return newAccount;
        });

    }

    @Override
    public void remove(Account account) {

    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return withSession(session -> session.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                .setParameter("accountNumber", accountNumber)
                .uniqueResult());
    }

}
