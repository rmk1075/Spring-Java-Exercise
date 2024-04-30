package com.exercise.springjava.virtualThread.service;

import org.springframework.stereotype.Service;

@Service
public class VirtualThreadExerciseService {
  public void printThreadId() {
    System.out.println("current thread: " + Thread.currentThread().threadId());
    Thread.ofVirtual().start(
        () -> {
          System.out.println("virtual thread: " + Thread.currentThread().threadId());
        }
    );
  }
}
