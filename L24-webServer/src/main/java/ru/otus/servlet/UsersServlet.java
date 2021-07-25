package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ru.otus.db.core.repository.DataTemplateHibernate;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.db.crm.model.User;
import ru.otus.db.crm.service.DbServiceClientImpl;
import ru.otus.services.TemplateProcessor;


public class UsersServlet extends HttpServlet {

  private static final String USERS_PAGE_TEMPLATE = "users.html";

  private final TemplateProcessor templateProcessor;
  private final TransactionManager transactionManager;

  public UsersServlet(TemplateProcessor templateProcessor, TransactionManager transactionManager) {
    this.templateProcessor = templateProcessor;
    this.transactionManager = transactionManager;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
    Map<String, Object> paramsMap = new HashMap<>();
    var dbServiceClient = new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(User.class));

    var userSelected = dbServiceClient.findAll();
    paramsMap.put("users", userSelected);

    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
  }

}
