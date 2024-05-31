package com.example.spring_virtual_thread.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class VirtualThreadService {
  public void printThreadId() {
    System.out.println("current thread: " + Thread.currentThread().threadId());
    Thread.ofVirtual().start(
        () -> {
          System.out.println("virtual thread: " + Thread.currentThread().threadId());
        }
    );
  }

  public void executeEmptyTaskByVirtualThread(int n) {
    List<Thread> threads = IntStream.range(0, n)
      .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {}))
      .toList();
    threads.forEach(Thread::start);
  }

  public void executeEmtpyTaskByThread(int n) {
    List<Thread> threads = IntStream.range(0, n)
      .mapToObj(i -> new Thread(() -> {}))
      .toList();
    threads.forEach(Thread::start);
  }

  public void executeBlockingTask() throws InterruptedException {
    Thread.sleep(1000);
  }

  public void executeEmptyTask() {
    for (int i = 0; i < 1000000; i++) {}
  }
}
