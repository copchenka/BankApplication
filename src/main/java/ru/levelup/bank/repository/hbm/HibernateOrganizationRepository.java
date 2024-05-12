package ru.levelup.bank.repository.hbm;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.domain.Organization;
import ru.levelup.bank.repository.OrganizationRepository;

import java.util.List;


public class HibernateOrganizationRepository extends AbstractRepository implements OrganizationRepository {

    public HibernateOrganizationRepository(SessionFactory factory) {
        super(factory);
    }

    @Override
    public List<Organization> all() {
        return withSession(session -> session.createQuery("from Organization", Organization.class)//
                .list());
    }

    @Override
    public void update(Organization organization) {
        try (Session session = factory.openSession()) {
            // ACID
            // A - Atomicity-транзакция неделима, иначе rollback(откат)
            // C - Consistency - целостность данных, удовлетворяют всем связям и условиям
            // I - Isolation - изолированность транзакций, нельзя одновременно читать и изменять
            // D - Durability - долговечность/доступнойсть данных, после транзакции они сохраняются на диске
            //writelog - запись в лог(коммиты) потом все паком закидываем в бд
            Transaction tx = session.beginTransaction();

            // 1 var
            // session.merge(organization);
            // только когда 100% есть объект, не  уддобно когда хочешь обновить часть полей
            //а надо создавать весь объект

            // 2 var
            Organization organizationFromDB = session.get(Organization.class, organization.getId());
            organizationFromDB.setName(organization.getName());
            organizationFromDB.setVatin(organization.getVatin());
            // session.merge() - нет необходимости вызывать метод merge
            // hibernate отправляет все одним пулом, при закрытии сессии, merge делать не надо.сделает автоматически
            tx.commit();
        }
    }

    @Override
    public void assignCustomerToOrganization(Integer organizationId, Integer customerId) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            //1 var -очень много запросов, не лучший вариант
//            Organization organization = session.get(Organization.class, organizationId);
//            Customer customer = session.get(Customer.class, customerId);
//            customer.getOrganizations().add(organization);
            //2 var
            Organization organization = session.getReference(Organization.class, organizationId);
            Customer customer = session.get(Customer.class, customerId);
            customer.getOrganizations().add(organization);

            tx.commit();
        }
    }

    @Override
    public Organization byVatin(String vatin) {
        return withSession(session -> session
                .createQuery("from Organization where vatin = :vatin", Organization.class)
                .setParameter("vatin", vatin)
                .uniqueResult());
    }

    @Override
    public List<Organization> byName(String name) {
        return null;
    }

    public Organization create(Integer id, String name, String vatin) {
        return withTransaction(session -> {
            Organization organization = new Organization(null, name, vatin);
            session.persist(organization);
            return organization;
        });
    }
}

