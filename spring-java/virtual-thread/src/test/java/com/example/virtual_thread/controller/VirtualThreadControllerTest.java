package com.example.virtual_thread.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.example.virtual_thread.controller.request.ExecutionEmptyRequest;
import com.example.virtual_thread.controller.response.ExecutionTimeResponse;
import com.example.virtual_thread.service.VirtualThreadService;

public class VirtualThreadControllerTest {
  private final VirtualThreadController vtController = new VirtualThreadController(
    new VirtualThreadService()
  );

  private final int num = 100;

  @Test
  public void executeCpuBoundTasks() {
    ExecutionEmptyRequest request = new ExecutionEmptyRequest();
    request.setNum(num);
    ExecutionTimeResponse response = vtController.executeEmptyTaskByThread(request);
    System.out.println("CPU bound tasks on Thread: " + response.getExecutionTime());

    response = vtController.executeEmptyTaskByVirtualThread(request);
    System.out.println("CPU bound tasks on Virtual Thread: " + response.getExecutionTime());
  }

  @Test
  public void executeFakeIoBoundTasks() {
    int n = 4000;
    long time = executeBlockingTaskByThread(n);
    System.out.println("Blocking Task by Thread: " + time);

    time = executeBlockingTaskByVirtualThread(n);
    System.out.println("Blocking Task by Virtual Thread: " + time);
  }

  private long executeBlockingTaskByThread(int n) {
    long start = System.currentTimeMillis();
    List<Thread> threads = IntStream.range(0, n)
      .mapToObj(i -> new Thread(() -> {
        try {
          vtController.executeBlockingTask();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }}
      ))
      .toList();
    threads.forEach(Thread::start);
    threads.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    return System.currentTimeMillis() - start;
  }

  private long executeBlockingTaskByVirtualThread(int n) {
    long start = System.currentTimeMillis();
    List<Thread> threads = IntStream.range(0, n)
      .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
        try {
          vtController.executeBlockingTask();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }}
      ))
      .toList();
    threads.forEach(Thread::start);
    threads.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
    return System.currentTimeMillis() - start;
  }
}
