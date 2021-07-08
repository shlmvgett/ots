package ru.otus.jdbc;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;

public class HomeWorkJdbc {

  private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
  private static final String USER = "usr";
  private static final String PASSWORD = "pwd";

  private static final Logger log = LoggerFactory.getLogger(HomeWorkJdbc.class);

  public static void main(String[] args) {
    var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
    flywayMigrations(dataSource);
    var transactionRunner = new TransactionRunnerJdbc(dataSource);
    var dbExecutor = new DbExecutorImpl();

    EntityClassMetaData<Client> clientMetaData = new EntityClassMetaDataImpl<>(Client.class);
    var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, clientMetaData);

    var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
    var client1 = dbServiceClient.saveClient(new Client("client1"));
    var client2 = dbServiceClient.saveClient(new Client("client2"));

    long t1 = System.currentTimeMillis();
    dbServiceClient.getClient(client1.getId());
    long t2 = System.currentTimeMillis();
    dbServiceClient.getClient(client1.getId());
    long t3 = System.currentTimeMillis();
    dbServiceClient.getClient(client2.getId());
    long t4 = System.currentTimeMillis();

    /**
     * As expected: (t3 - t2) < (t4 - t3) <= (t2 - t1)
     * Cached response is ~40% faster
     **/
    log.info("Empty cache: {}ms", (t2 - t1));
    log.info("Not empty cache: {}ms", (t3 - t2));
    log.info("Empty cache: {}ms (for warmed up app)", (t4 - t3));
  }

  private static void flywayMigrations(DataSource dataSource) {
    log.info("db migration started...");
    var flyway = Flyway.configure()
        .dataSource(dataSource)
        .locations("classpath:/db/migration")
        .load();
    flyway.migrate();
    log.info("db migration finished.");
    log.info("***");
  }
}
