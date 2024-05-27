package com.example.spring_grpc;

import com.exercise.springjava.grpc.DemoTestGrpc;
import com.exercise.springjava.grpc.DemoTestOuterClass.DemoRequest;
import com.exercise.springjava.grpc.DemoTestOuterClass.DemoResponse;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SpringGrpcService extends DemoTestGrpc.DemoTestImplBase {
  @Override
  public void unary(
    DemoRequest request,
    StreamObserver<DemoResponse> responseObserver
  ) {
    int a = request.getA();
    int b = request.getB();

    // return the value.
    responseObserver.onNext(generateDemoResponse(a + b));

    // specify that we finished dealing with the RPC
    responseObserver.onCompleted();
  }

  private DemoResponse generateDemoResponse(int result) {
    return DemoResponse.newBuilder()
      .setResult(result)
      .build();
  }

  @Override
  public void serverSideStreaming(
    DemoRequest request,
    StreamObserver<DemoResponse> responseObserver
  ) {
    int a = request.getA();
    int b = request.getB();
    int result = a + b;
    for (int i = 0; i < 10; i++) {
      responseObserver.onNext(generateDemoResponse(result));
      result += a + b;
    }
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<DemoRequest> clientSideSteraming(
    StreamObserver<DemoResponse> responseOberver
  ) {
    return new StreamObserver<DemoRequest>() {
      int sum = 0;
      int count = 0;

      @Override
      public void onNext(DemoRequest value) {
        int a = value.getA();
        int b = value.getB();
        this.sum += a + b;
        this.count++;
      }

      @Override
      public void onError(Throwable t) {
        System.out.println(t.getMessage());
      }

      @Override
      public void onCompleted() {
        System.out.println("count=" + this.count + " sum=" + this.sum);
        responseOberver.onNext(generateDemoResponse(sum));
        responseOberver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<DemoRequest> bidirectionalStreaming(
    StreamObserver<DemoResponse> responseObserver
  ) {
    return new StreamObserver<DemoRequest>() {

      @Override
      public void onNext(DemoRequest value) {
        int a = value.getA();
        int b = value.getB();
        responseObserver.onNext(generateDemoResponse(a + b));
      }

      @Override
      public void onError(Throwable t) {
        System.out.println(t.getMessage());
      }

      @Override
      public void onCompleted() {
        responseObserver.onCompleted();
      }
    };
  }
}
