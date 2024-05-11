package ru.levelup.bank.repository.hbm;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor

public abstract class AbstractRepository {
    protected final SessionFactory factory;

    //Метод позволяющий выполнить код в рамках session
    public void withSessionNoResults(Consumer<Session> block) {
        try (Session session = factory.openSession()) {
            block.accept(session);
        }
    }

    public <T> T withSession(Function<Session, T> func) {
        try (Session session = factory.openSession()) {
            return func.apply(session);
        }
    }

    public <T> T withTransaction(Function<Session, T> func) {
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            T result = func.apply(session);
            tx.commit();
            return result;
        }
    }
}
