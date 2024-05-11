package ru.levelup.bank.menu.action;

import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.ConsoleMenu;
import ru.levelup.bank.menu.MenuAction;
import ru.levelup.bank.repository.CustomerRepository;
import ru.levelup.bank.repository.jdbc.JdbcCustomerRepository;
@MenuAction(actionCode = 3)

public class CustomerDetailsMenuAction implements Action {
    private final CustomerRepository customerRepository;

    public CustomerDetailsMenuAction() {
        this.customerRepository = new JdbcCustomerRepository(new JdbcConnectionManager());
    }

    @Override
    public void execute() {
        int customerId = ConsoleMenu.readInt("Введите id клиента");
        Customer customer = customerRepository.byId(customerId);
        if (customer == null) {
            System.out.println("Клиента с " + customerId + " не существует");
        } else {
            System.out.println(customer);
        }
    }
}
