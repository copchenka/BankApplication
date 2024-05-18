package ru.levelup.bank.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.levelup.bank.configuration.files.ConfigurationFileLoader;
import ru.levelup.bank.domain.Account;
import ru.levelup.bank.domain.Customer;
import ru.levelup.bank.domain.Organization;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class HibernateConfiguration {
    private static SessionFactory factory;

    public static void initializeSessionFactory() {
        if (factory != null) {
            throw new RuntimeException("SessionFactory has been already initialized");
        }


        Properties properties =buildHibernateProperties();


        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(properties)
                .build();

        Configuration configuration = new Configuration()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Organization.class)
                .addAnnotatedClass(Account.class);
        //.addAnnotatedClass(Customer.class);
        factory = configuration.buildSessionFactory(registry);
    }

    public static Properties buildHibernateProperties() {
        Map<String, String> configurationProperties = ConfigurationFileLoader.LoadConfiguration();

        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", configurationProperties.get("db.driver"));
        properties.setProperty("hibernate.connection.url", configurationProperties.get("db.url"));
        properties.setProperty("hibernate.connection.username", configurationProperties.get("db.username"));
        properties.setProperty("hibernate.connection.password", configurationProperties.get("db.password"));

        properties.setProperty("hibernate.show_sql", configurationProperties.get("db.debug.show.sql"));
        properties.setProperty("hibernate.format_sql", configurationProperties.get("db.debug.format.sql"));
        properties.setProperty("hibernate.hbm2ddl.auto", configurationProperties.get("db.schema.ddl.type"));
        return properties;
    }

    public static SessionFactory getFactory() {
        return factory;
    }
}
