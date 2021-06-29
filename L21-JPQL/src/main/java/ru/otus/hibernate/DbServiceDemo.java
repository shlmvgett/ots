package ru.otus.hibernate;

import java.util.ArrayList;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hibernate.core.repository.DataTemplateHibernate;
import ru.otus.hibernate.core.repository.HibernateUtils;
import ru.otus.hibernate.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.hibernate.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.hibernate.crm.model.Address;
import ru.otus.hibernate.crm.model.Client;
import ru.otus.hibernate.crm.model.Phone;
import ru.otus.hibernate.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

  private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);
  private static final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

  public static void main(String[] args) {
    var dbUrl = configuration.getProperty("hibernate.connection.url");
    var dbUserName = configuration.getProperty("hibernate.connection.username");
    var dbPassword = configuration.getProperty("hibernate.connection.password");

    new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

    var sessionFactory =
        HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
    var transactionManager = new TransactionManagerHibernate(sessionFactory);

    var clientTemplate = new DataTemplateHibernate<>(Client.class);

    var client1 = new Client("client_1");
    client1.setAddress(new Address("street_1"));
    var phones1 = new ArrayList<Phone>();
    phones1.add(new Phone("phone_1", client1));
    phones1.add(new Phone("phone_2", client1));
    client1.setPhones(phones1);

    // Add Client 1
    var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    dbServiceClient.saveClient(client1);

    var client2 = new Client("client_2");
    client2.setAddress(new Address("street_2"));
    var phones2 = new ArrayList<Phone>();
    phones2.add(new Phone("phone_3", client2));
    phones2.add(new Phone("phone_4", client2));
    client2.setPhones(phones2);

    // Add Client 2
    dbServiceClient.saveClient(client2);

    var client2Selected = dbServiceClient.getClient(client2.getId())
        .orElseThrow(() -> new RuntimeException("Client wasn't found, id:" + client2.getId()));
    log.info("Select for client_2: {}", client2Selected);

    client2Selected.setName("client_2_updated");
    client2Selected.setAddress(new Address("street_updated"));
    dbServiceClient.saveClient(client2Selected);
    log.info("Update for client_2: {}", client2Selected);

    log.info("Select all clients:");
    dbServiceClient.findAll().forEach(client -> log.info("Client: {}", client));
  }
}
