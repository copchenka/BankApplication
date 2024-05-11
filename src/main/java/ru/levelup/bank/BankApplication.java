package ru.levelup.bank;


import ru.levelup.bank.configuration.HibernateConfiguration;
import ru.levelup.bank.domain.AccountJdbc;
import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.jdbc.JdbcConnectionManager;
import ru.levelup.bank.menu.Action;
import ru.levelup.bank.menu.ConsoleMenu;
import ru.levelup.bank.menu.MenuActions;
import ru.levelup.bank.repository.AccountRepository;
import ru.levelup.bank.repository.AccountRepositoryJdbc;
import ru.levelup.bank.repository.CustomerRepository;
import ru.levelup.bank.repository.jdbc.JdbcAccountRepository;
import ru.levelup.bank.repository.jdbc.JdbcCustomerRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class BankApplication {
//    public static void main(String[] args) {
//        JdbcConnectionManager cm = new JdbcConnectionManager();
//        CustomerRepository customerRepository = new JdbcCustomerRepository(cm);
//        List<Customer> all = customerRepository.all();
//        for (Customer cs : all) {
//            System.out.println(cs);
//        }

//        Customer customer=customerRepository.byId(3);
//        System.out.println(customer);

//        Customer createdCustomer = customerRepository.create(
//                178342893L,
//                "Kapusta",
//                "Ezhenko",
//                Date.valueOf(LocalDate.of(1992, 3, 30)),
//                "4001",
//                "432167"
//        );
//        System.out.println(createdCustomer);

//        OrganizationRepository organizationRepository = new JdbcOrganizationRepository(cm);
//        List<Organization> organizations = organizationRepository.all();
//        organizations.forEach(System.out::println);
//        Organization organization = new Organization(4, "ЗАО Кошечки+1", "39865438123903");
//        organizationRepository.update(organization);
//        organizations = organizationRepository.all();
//        organizations.forEach(System.out::println);
//
//
//        System.out.println(organizationRepository.byVatin("39812838123903"));
//
//        PaymentRepository paymentRepository = new JdbcPaymentRepository();
//        paymentRepository.changeStatus(1, PaymentStatus.NEW);
//        paymentRepository.changeStatus(2, "CONFIRM");


//        AccountRepositoryJdbc accountRepository = new JdbcAccountRepository(cm);
////        List<Account> accounts = accountRepository.all();
////        accounts.forEach(System.out::println);
//
//        AccountJdbc account = new AccountJdbc(
//                1,
//                "12345678888876543",//12345678909876543
//                Date.valueOf(LocalDate.of(1992, 3, 30)),
//                "Действующий",
//                "Расчетный",
//                10000013L
//        );
//        accountRepository.update(account);
        //accountRepository.remove(account);
//        accounts = accountRepository.all();
//        accounts.forEach(System.out::println);


//        PaymentRepository paymentRepository = new JdbcPaymentRepository(cm);
//        List<Payment> payments = paymentRepository.byAccountId(account);
//     payments.forEach(System.out::println);

//        paymentRepository.createPayment(Date.valueOf(LocalDate.of(1992, 3, 30)), 665L, 3, 2, PaymentStatus.FAILED);
//        payments = paymentRepository.all();
//        payments.forEach(System.out::println);
//    }

    public static void main(String[] args) {
        HibernateConfiguration.initializeSessionFactory();
        MenuActions.initializeMenu();
        int actionCode = 0;
        do {
            ConsoleMenu.printGeneralMenu();
            actionCode = ConsoleMenu.readInt("Введите номер действия");
            Action action = MenuActions.getAction(actionCode);
            if (action == null) {
                System.out.println("Такого действия не существует");
            } else {
                action.execute();
            }
        } while (actionCode != 0);
        HibernateConfiguration.getFactory().close();
    }
}
