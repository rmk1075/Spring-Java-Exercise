package com.exercise.springjava.grpc;

import java.io.IOException;

import com.exercise.springjava.grpc.DemoTestOuterClass.DemoRequest;
import com.exercise.springjava.grpc.DemoTestOuterClass.DemoResponse;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class GrpcServer {

  private Server server;
  private int port;

  private static class DemoTestServer extends DemoTestGrpc.DemoTestImplBase {
    /**
     * StreamObserver: response observer, which is a special interface for server to call with its response
     */
    @Override
    public void unary(
      DemoRequest request,
      StreamObserver<DemoResponse> responseObserver
    ) {
      // return the value.
      responseObserver.onNext(
        generateDemoResponse(
          Integer.parseInt(request.getId())
        )
      );

      // specify that we finished dealing with the RPC
      responseObserver.onCompleted();
    }

    private DemoResponse generateDemoResponse(int id) {
      return DemoResponse.newBuilder()
        .setId(String.valueOf(id))
        .setTimestamp(String.valueOf(System.currentTimeMillis()))
        .build();
    }

    @Override
    public void serverSideStreaming(
      DemoRequest request,
      StreamObserver<DemoResponse> responseObserver
    ) {
      int id = Integer.parseInt(request.getId());
      for (int i = 0; i < 100; i++) {
        responseObserver.onNext(generateDemoResponse(id + i));
      }
      responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DemoRequest> clientSideSteraming(
      StreamObserver<DemoResponse> responseOberver
    ) {
      return new StreamObserver<DemoRequest>() {
        int count = 0;
        int maxId = -1;

        @Override
        public void onNext(DemoRequest value) {
          String id = value.getId();
          System.out.println(
            "request id=" + id + " timestamp=" + value.getTimestamp());
          this.maxId = Math.max(this.maxId, Integer.parseInt(id));
          this.count++;
        }

        @Override
        public void onError(Throwable t) {
          throw new UnsupportedOperationException("Unimplemented method 'onError'");
        }

        @Override
        public void onCompleted() {
          System.out.println("count=" + this.count + " maxId=" + this.maxId);
          responseOberver.onNext(generateDemoResponse(this.maxId));
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
          String id = value.getId();
          System.out.println(
            "request id=" + id + " timestamp=" + value.getTimestamp());
          responseObserver.onNext(generateDemoResponse(Integer.parseInt(id)));
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

  public GrpcServer(int port) {
    this.server = ServerBuilder
      .forPort(port)
      .addService(new DemoTestServer())
      .build();
    this.port = port;
  }

  public void start() throws IOException {
    this.server.start();
    System.out.println("Server started. Listening on " + this.port);
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    int port = 50051;
    GrpcServer server = new GrpcServer(port);
    server.start();
    server.blockUntilShutdown();
  }
}
