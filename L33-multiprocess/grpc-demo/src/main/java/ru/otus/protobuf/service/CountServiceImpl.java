package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountMessage;
import ru.otus.protobuf.generated.CountResponseMessage;
import ru.otus.protobuf.generated.CountServiceGrpc;

public class CountServiceImpl extends CountServiceGrpc.CountServiceImplBase {

  @Override
  public void getCount(CountMessage countMessage, StreamObserver<CountResponseMessage> responseObserver) {
    for (long i = countMessage.getInitValue(); i <= countMessage.getMaxValue(); i++) {
      responseObserver.onNext(CountResponseMessage.newBuilder().setServerValue(i).build());
      sleep(2000);
    }
    responseObserver.onCompleted();
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
