package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import ru.otus.db.core.repository.HibernateUtils;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.db.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.db.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.db.crm.model.User;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithFilterBasedSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

/*
    // Стартовая страница
    http://localhost:8080
*/
public class WebServerWithFilterBasedSecurityDemo {

  private static final int WEB_SERVER_PORT = 8080;
  private static final String TEMPLATES_DIR = "/templates/";
  private static final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");


  public static void main(String[] args) throws Exception {
    var transactionManager = setupDb();

    Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

    UserAuthService authService = new UserAuthServiceImpl(transactionManager);
    UsersWebServer usersWebServer =
        new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT, authService, gson, templateProcessor, transactionManager);

    usersWebServer.start();
    usersWebServer.join();
  }

  private static TransactionManager setupDb() {
    var dbUrl = configuration.getProperty("hibernate.connection.url");
    var dbUserName = configuration.getProperty("hibernate.connection.username");
    var dbPassword = configuration.getProperty("hibernate.connection.password");

    new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
    var dbSession = HibernateUtils.buildSessionFactory(configuration, User.class);
    return new TransactionManagerHibernate(dbSession);
  }
}
