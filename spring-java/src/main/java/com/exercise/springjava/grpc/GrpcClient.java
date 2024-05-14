package com.exercise.springjava.grpc;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.exercise.springjava.grpc.DemoTestGrpc.DemoTestBlockingStub;
import com.exercise.springjava.grpc.DemoTestGrpc.DemoTestStub;
import com.exercise.springjava.grpc.DemoTestOuterClass.DemoRequest;
import com.exercise.springjava.grpc.DemoTestOuterClass.DemoResponse;

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
      int id = 1;

      // unary
      client.callUnary(id);
      id++;

      // server streaming
      client.callServerSideStreaming(id);
      id++;

      // client streaming
      client.callClientSideStreaming(id, id + 99);
      id += 100;

      // bidirectional streaming
      client.callBidirectionalStreaming(id, id + 100);
    } finally {
      // close channel?
    }
  }

  private void callUnary(int id) {
    System.out.println("Call unary. id=" + id);
    DemoRequest request = generateDemoRequest(id);
    try {
      DemoResponse response = syncStub.unary(request);
      assert response.getId() == String.valueOf(id);
    } catch (StatusRuntimeException e) {
      System.out.println("RPC failed: " + e.getStatus());
      return ;
    }
  }

  private DemoRequest generateDemoRequest(int id) {
    return DemoRequest.newBuilder()
      .setId(String.valueOf(id))
      .setTimestamp(String.valueOf(System.currentTimeMillis()))
      .build();
  }

  private void callServerSideStreaming(int id) {
    System.out.println("Call server side streaming. id=" + id);
    DemoRequest request = generateDemoRequest(id);
    try {
      Iterator<DemoResponse> responses = syncStub.serverSideStreaming(request);
      while (responses.hasNext()) {
        System.out.println(responses.next().getId());
      }
    } catch (StatusRuntimeException e) {
      System.out.println("RPC failed: " + e.getStatus());
      return ;
    }
  }

  private void callClientSideStreaming(int startId, int endId) throws InterruptedException {
    System.out.println(
      "Call cliend side streaming. startId=" + startId + " endId=" + endId
    );
    validateIdRange(startId, endId);
    final CountDownLatch finishLatch = new CountDownLatch(1);
    StreamObserver<DemoResponse> responseObserver = new StreamObserver<DemoResponse>() {
      @Override
      public void onNext(DemoResponse value) {
        String id = value.getId();
        assert endId == Integer.parseInt(id);
        System.out.println(id);
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("client side streaming is failed. " + t.getMessage());
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
      for (int i = startId; i <= endId; i++) {
        requestObserver.onNext(generateDemoRequest(i));
        Thread.sleep(rand.nextInt(100) + 500);
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

  private void validateIdRange(int startId, int endId) {
    if (endId < startId) {
      throw new IllegalArgumentException(
        "endId is smaller than startId. startId=" + startId + " endId=" + endId
      );
    }
  }

  private void callBidirectionalStreaming(int startId, int endId) throws InterruptedException {
    System.out.println(
      "Call bidirectional streaming. startId=" + startId + " endId=" + endId
    );
    validateIdRange(startId, endId);
    final CountDownLatch finishLatch = new CountDownLatch(1);
    StreamObserver<DemoResponse> responseObserver = new StreamObserver<DemoResponse>() {
      @Override
      public void onNext(DemoResponse value) {
        String id = value.getId();
        assert endId == Integer.parseInt(id);
        System.out.println(id);
      }

      @Override
      public void onError(Throwable t) {
        System.out.println("client side streaming is failed. " + t.getMessage());
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
      for (int i = startId; i <= endId; i++) {
        requestObserver.onNext(generateDemoRequest(i));
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
