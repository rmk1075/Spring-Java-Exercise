package grpc.practice;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import grpc.practice.DemoTestGrpc.DemoTestBlockingStub;
import grpc.practice.DemoTestGrpc.DemoTestStub;
import grpc.practice.DemoTestOuterClass.DemoRequest;
import grpc.practice.DemoTestOuterClass.DemoResponse;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class GrpcClient {

  /**
   * - channel
   * TCP connection between gRPC server and client.
   * 
   * - blocking/synchronous stub
   * The RPC call waits for the server to respond, and will either return a response or raise an exception.
   * 
   * - non-blocking/asynchronous stub:
   * This stub makes non-blocking calls to the server, where the response is returned asynchronously.
   * You can make certain types of streaming call only using the asynchronous stub.
   */
  private Channel channel;
  private DemoTestStub asyncStub;
  private DemoTestBlockingStub syncStub;

  public GrpcClient(String host, int port) {
    this.channel = ManagedChannelBuilder
      .forAddress(host, port)
      .usePlaintext()
      .build();
    this.asyncStub = DemoTestGrpc.newStub(channel);
    this.syncStub = DemoTestGrpc.newBlockingStub(channel);
  }

  public static void main(String[] args) throws InterruptedException {
    String host = "localhost";
    int port = 50051;
    if (0 < args.length) {
      port = Integer.parseInt(args[0]);
    }

    try {
      GrpcClient client = new GrpcClient(host, port);

      // unary
      client.callUnary(1, 2);

      // server streaming
      client.callServerSideStreaming(1, 2);

      // client streaming
      client.callClientSideStreaming(10);

      // bidirectional streaming
      client.callBidirectionalStreaming(10);
    } finally {
      // close channel?
    }
  }

  private void callUnary(int a, int b) {
    DemoRequest request = generateDemoRequest(a, b);
    try {
      DemoResponse response = syncStub.unary(request);
      System.out.println("**** unary ****");
      System.out.println("a + b = " + response.getResult());
    } catch (StatusRuntimeException e) {
      System.out.println("RPC failed: " + e.getStatus());
      return ;
    }
  }

  private DemoRequest generateDemoRequest(int a, int b) {
    return DemoRequest.newBuilder().setA(a).setB(b).build();
  }

  private void callServerSideStreaming(int a, int b) {
    DemoRequest request = generateDemoRequest(a, b);
    try {
      Iterator<DemoResponse> responses = syncStub.serverSideStreaming(request);
      System.out.println("**** server side streaming ****");
      System.out.println("a = " + a + ", b = " + b);
      while (responses.hasNext()) {
        System.out.println(responses.next().getResult());
      }
    } catch (StatusRuntimeException e) {
      System.out.println("RPC failed: " + e.getStatus());
      return ;
    }
  }

  private void callClientSideStreaming(int count) throws InterruptedException {
    final CountDownLatch finishLatch = new CountDownLatch(1);
    StreamObserver<DemoResponse> responseObserver = new StreamObserver<DemoResponse>() {
      @Override
      public void onNext(DemoResponse value) {
        System.out.println(value.getResult());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println(t.getMessage());
        finishLatch.countDown();
      }

      @Override
      public void onCompleted() {
        finishLatch.countDown();
      }
    };

    StreamObserver<DemoRequest> requestObserver =
      asyncStub.clientSideSteraming(responseObserver);
    try {
      Random rand = new Random();
      System.out.println("**** client side streaming ****");
      for (int i = 0; i < count; i++) {
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        System.out.println("a = " + a + ", b = " + b);
        requestObserver.onNext(generateDemoRequest(a, b));
      }
    } catch (RuntimeException e) {
      requestObserver.onError(e);
      throw e;
    }

    requestObserver.onCompleted();
    finishLatch.await(1, TimeUnit.MINUTES);
  }

  private void callBidirectionalStreaming(int count) throws InterruptedException {
    final CountDownLatch finishLatch = new CountDownLatch(1);
    StreamObserver<DemoResponse> responseObserver = new StreamObserver<DemoResponse>() {
      @Override
      public void onNext(DemoResponse value) {
        System.out.println(value.getResult());
      }

      @Override
      public void onError(Throwable t) {
        System.out.println(t.getMessage());
        finishLatch.countDown();
      }

      @Override
      public void onCompleted() {
        finishLatch.countDown();
      }
    };

    StreamObserver<DemoRequest> requestObserver
      = asyncStub.bidirectionalStreaming(responseObserver);
    try {
      Random rand = new Random();
      System.out.println("**** bidirectional streaming ****");
      for (int i = 0; i < count; i++) {
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        System.out.println("a = " + a + ", b = " + b);
        requestObserver.onNext(generateDemoRequest(a, b));
        if (finishLatch.getCount() == 0) {
          return ;
        }
      }
    } catch (RuntimeException e) {
      requestObserver.onError(e);
      throw e;
    }

    requestObserver.onCompleted();
    finishLatch.await(1, TimeUnit.MINUTES);
  }
}
