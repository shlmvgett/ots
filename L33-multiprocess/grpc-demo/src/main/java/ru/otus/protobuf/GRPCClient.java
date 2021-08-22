package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountMessage;
import ru.otus.protobuf.generated.CountResponseMessage;
import ru.otus.protobuf.generated.CountServiceGrpc;

import java.util.concurrent.atomic.AtomicLong;

public class GRPCClient {

  private static final String SERVER_HOST = "localhost";
  private static final int SERVER_PORT = 8190;

  private static AtomicLong serverValue = new AtomicLong(0);

  public static void main(String[] args) {
    ManagedChannel channel = ManagedChannelBuilder
        .forAddress(SERVER_HOST, SERVER_PORT)
        .usePlaintext()
        .build();

    CountMessage countMessage = CountMessage.newBuilder().setInitValue(0).setMaxValue(30).build();

    CountServiceGrpc.CountServiceStub newStub = CountServiceGrpc.newStub(channel);
    newStub.getCount(countMessage, new StreamObserver<CountResponseMessage>() {
      @Override
      public void onNext(CountResponseMessage countResponseMessage) {
        serverValue.set(countResponseMessage.getServerValue());
        System.out.println("onNext serverValue: " + serverValue);
      }

      @Override
      public void onError(Throwable t) {
        System.err.println(t);
      }

      @Override
      public void onCompleted() {
        System.out.println("Stream: onCompleted");
      }
    });

    long lastServerValue = 0;
    long currentClientValue = 0;
    for (int i = 0; i < 50; i++) {
      long currentServerValue = serverValue.get();
      if(currentServerValue != lastServerValue) {
        currentClientValue = currentClientValue + currentServerValue + 1;
      } else {
        currentClientValue = currentClientValue + 1;
      }
      lastServerValue = currentServerValue;
      System.out.println("i: " + i + ", currentClientValue: " + currentClientValue);
      sleep(1000);
    }
    channel.shutdown();
  }

  private static void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
