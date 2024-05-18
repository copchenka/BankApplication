package ru.levelup.bank.menu.action;

import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.ConsoleMenu;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.AccountRepository;
import ru.levelup.bank.repository.hbm.HibernateAccountRepository;

import java.sql.Date;
import java.time.LocalDate;

@MenuAction(actionCode = 8)
public class CreateAccountMenuAction implements Action {
    private final HibernateAccountRepository accountRepository;

    public CreateAccountMenuAction() {
        this.accountRepository = new HibernateAccountRepository(HibernateConfiguration.getFactory());
    }

    @Override
    public void execute() {
        String accountNumber = ConsoleMenu.readString("Введите номер аккаунта");
        if (accountRepository.getAccountByAccountNumber(accountNumber) == null) {
            String status = ConsoleMenu.readString("Введите статус аккаунта");
            String type = ConsoleMenu.readString("Введите тип аккаунта");
            String openDatetime = ConsoleMenu.readString("Введите дату открытия аккаунта. Введите 0, если нужна сегодняшняя дата.");
            if (openDatetime.equals("0")) {
                openDatetime = String.valueOf(LocalDate.now());
            }
            long bankDocumentId = ConsoleMenu.readInt("Введите id клиента");
            Account account = accountRepository.create(accountNumber, Date.valueOf(openDatetime), status, type, bankDocumentId);
            if (account != null) {
                System.out.println("Аккаунт успешно создан");
            }
        } else {
            System.out.println("Аккаунт с таким номером уже существует");
        }
    }
}
