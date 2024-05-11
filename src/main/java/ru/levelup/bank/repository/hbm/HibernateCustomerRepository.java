package ru.levelup.bank.repository.hbm;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.repository.CustomerRepository;

import java.sql.Date;
import java.util.List;


public class HibernateCustomerRepository extends AbstractRepository implements CustomerRepository {


    public HibernateCustomerRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<Customer> all() {
        return withSession(session -> session.createQuery("from Customer", Customer.class)//
                .list());

//        try (Session session = factory.openSession()) {
//            //чтение можно провести без транзакций
//            //hql hibernate query language
//            return session.createQuery("from Customer", Customer.class)//
//                    .list();
//        }
    }

    @Override
    public Customer byId(Integer customerId) {
        return withSession(session -> session
                .get(Customer.class, customerId));

//        try (Session session = factory.openSession()) {
//
//            return session
//                    .get(Customer.class, customerId);//всегда по идентификатору
////            var2 для любого,where тд
////            session.createQuery("from Customer where id = :gg", Customer.class)//
////                    .setParameter("gg",customerId)
////                    .uniqueResult();
//
//        }
    }

    @Override
    public Customer create(
            long bdId,
            String firstName,
            String lastName,
            Date birthday,
            String passportSeria,
            String passportNumber
    ) {
        return withTransaction(session -> {
            Customer customer = new Customer(
                    null,
                    bdId,
                    firstName,
                    lastName,
                    birthday,
                    passportSeria,
                    passportNumber
            );
            session.persist(customer);
            return customer;
        });
//        try (Session session = factory.openSession()) {
//            Transaction tx = session.beginTransaction();//начало транзакции
//            Customer customer = new Customer(
//                    null,
//                    bdId,
//                    firstName,
//                    lastName,
//                    birthday,
//                    passportSeria,
//                    passportNumber
//            );
//            session.persist(customer); // insert into..
//            tx.commit(); // фиксация/завершение транзакции (успешное)
//            return customer;
//        }


    }
}
