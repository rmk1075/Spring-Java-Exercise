package com.exercise.springjava.virtualThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadExercise {
    public void executeCpuBoundTasks(int num, int type) {
        System.out.println("Execute CPU bound tasks");
        Runnable runnable = () -> IntStream.range(0, 100000000).forEach(Math::sin);

        System.out.println("Generate Threads");

        try (
            ExecutorService executor = Executors.newFixedThreadPool(1);
        ) {
            for (int i = 0; i < num; i++) {
                executor.submit(runnable);
            }
        }
    }

    public void executeFakeIoBoundTasks(int num, int type) {
        System.out.println("Execute IO bound tasks");
        Runnable runnable = () -> {
            int count = 100000000;
            IntStream.range(0, count / 2).forEach(Math::sin);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            IntStream.range(0, count / 2).forEach(Math::sin);
        };

        System.out.println("Generate Threads");

        try (
            ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        ) {
            for (int i = 0; i < num; i++) {
                executor.submit(runnable);
            }
        }

    }
}