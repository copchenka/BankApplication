package ru.levelup.bank.repository;

import ru.levelup.bank.domain.Customer;

import java.sql.Date;
import java.util.List;

public interface CustomerRepository {
    /**
     * Получение списка всех кастомеров
     *
     * @return список кастомеров
     */
    List<Customer> all();

    /**
     * Поиск кастомера по id
     *
     * @param customerId идентификатор(значение) в таблице customer
     * @return customer, если нет- null
     */
    Customer byId(Integer customerId);

    /**
     * Создание кастомера
     *
     * @param bdId           bank_document_id
     * @param firstName      имя
     * @param lastName       фамилия
     * @param birthday       др
     * @param passportSeria  Серия
     * @param passportNumber номер паспорта
     * @return
     */

    Customer create(
            long bdId,
            String firstName,
            String lastName,
            Date birthday,
            String passportSeria,
            String passportNumber
    );
}
