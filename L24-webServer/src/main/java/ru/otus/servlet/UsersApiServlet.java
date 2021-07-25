package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import lombok.SneakyThrows;
import ru.otus.db.core.repository.DataTemplateHibernate;
import ru.otus.db.core.sessionmanager.TransactionManager;
import ru.otus.db.crm.model.User;
import ru.otus.db.crm.service.DbServiceClientImpl;

public class UsersApiServlet extends HttpServlet {

  private static final int ID_PATH_PARAM_POSITION = 1;

  private final TransactionManager transactionManager;
  private final Gson gson;

  public UsersApiServlet(Gson gson, TransactionManager transactionManager) {
    this.gson = gson;
    this.transactionManager = transactionManager;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    var dbServiceClient =
        new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(User.class));
    var userSelected = dbServiceClient.getClient(extractIdFromRequest(request));

    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream out = response.getOutputStream();
    out.print(gson.toJson(userSelected.get()));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String requestData = extractBody(request);
    var dbServiceClient =
        new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(User.class));
    User user = dbServiceClient.saveClient(gson.fromJson(requestData, User.class));

    response.setContentType("application/json;charset=UTF-8");
    ServletOutputStream out = response.getOutputStream();
    out.print(gson.toJson(user));
  }

  @SneakyThrows
  private String extractBody(HttpServletRequest request) {
    BufferedReader br = request.getReader();
    String str;
    StringBuilder body = new StringBuilder();
    while ((str = br.readLine()) != null) {
      body.append(str);
    }
    return body.toString();
  }

  private long extractIdFromRequest(HttpServletRequest request) {
    String[] path = request.getPathInfo().split("/");
    String id = (path.length > 1) ? path[ID_PATH_PARAM_POSITION] : String.valueOf(-1);
    return Long.parseLong(id);
  }
}
