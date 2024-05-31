package grpc.practice;

import java.io.IOException;

import grpc.practice.DemoTestOuterClass.DemoRequest;
import grpc.practice.DemoTestOuterClass.DemoResponse;

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

  public GrpcServer(int port) {
    this.server = ServerBuilder
      .forPort(port)
      .addService(new DemoTestServer())
      .build();
    this.port = port;
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    int port = 50051;
    GrpcServer server = new GrpcServer(port);
    server.start();
    server.blockUntilShutdown();
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
}
