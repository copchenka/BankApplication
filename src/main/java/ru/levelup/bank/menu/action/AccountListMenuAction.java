package ru.levelup.bank.menu.action;

import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.AccountRepository;
import ru.levelup.bank.repository.hbm.HibernateAccountRepository;

import java.util.List;

@MenuAction(actionCode = 7)

public class AccountListMenuAction implements Action {
    private final AccountRepository accountRepository;

    public AccountListMenuAction() {
        this.accountRepository = new HibernateAccountRepository(HibernateConfiguration.getFactory());
    }

    @Override
    public void execute() {
        List<Account> accounts = accountRepository.all();
        if (accounts==null) System.out.println("Счетов нет");
        else{
            System.out.println("Список счетов: ");
            accounts.forEach(account -> System.out.println(account));
        }
    }
}
