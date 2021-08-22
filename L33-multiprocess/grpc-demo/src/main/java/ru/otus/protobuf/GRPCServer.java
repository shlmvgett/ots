package ru.otus.protobuf;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import ru.otus.protobuf.service.CountServiceImpl;

public class GRPCServer {

  public static final int SERVER_PORT = 8190;

  public static void main(String[] args) throws IOException, InterruptedException {

    CountServiceImpl countService = new CountServiceImpl();

    Server server = ServerBuilder
        .forPort(SERVER_PORT)
        .addService(countService).build();
    server.start();
    System.out.println("server waiting for client connections...");
    server.awaitTermination();
  }
}
