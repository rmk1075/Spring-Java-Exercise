package com.exercise.springjava.virtualThread;


import org.junit.jupiter.api.Test;

public class VirtualThreadExerciseTest {
  private final VirtualThreadExercise virtualThreadExercise = new VirtualThreadExercise();

  private final int num = 100;

  @Test
  public void executeCpuBoundTasks() {
    long start = System.currentTimeMillis();
    virtualThreadExercise.executeCpuBoundTasks(num, 0);
    System.out.println("CPU bound tasks on Thread: " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    virtualThreadExercise.executeCpuBoundTasks(num, 1);
    System.out.println("CPU bound tasks on Virtual Thread: " + (System.currentTimeMillis() - start));
  }

  @Test
  public void executeFakeIoBoundTasks() {
    long start = System.currentTimeMillis();
    virtualThreadExercise.executeFakeIoBoundTasks(num, 0);
    System.out.println("IO bound tasks on Thread: " + (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    virtualThreadExercise.executeFakeIoBoundTasks(num, 1);
    System.out.println("IO bound tasks on Virtual Thread: " + (System.currentTimeMillis() - start));
  }
}
