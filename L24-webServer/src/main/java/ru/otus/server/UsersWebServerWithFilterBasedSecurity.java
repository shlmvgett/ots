package ru.otus.server;

import com.google.gson.Gson;
import java.util.Arrays;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

public class UsersWebServerWithFilterBasedSecurity extends UsersWebServerSimple {

  private final UserAuthService authService;

  public UsersWebServerWithFilterBasedSecurity(
      int port,
      UserAuthService authService,
      Gson gson,
      TemplateProcessor templateProcessor,
      TransactionManager transactionManager) {
    super(port, gson, templateProcessor, transactionManager);
    this.authService = authService;
  }

  @Override
  protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
    servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
    AuthorizationFilter authorizationFilter = new AuthorizationFilter();
    Arrays.stream(paths).forEachOrdered(
        path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
    return servletContextHandler;
  }
}
