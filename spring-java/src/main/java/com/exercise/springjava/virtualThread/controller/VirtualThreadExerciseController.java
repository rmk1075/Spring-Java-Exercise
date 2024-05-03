package com.exercise.springjava.virtualThread.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercise.springjava.virtualThread.controller.request.ExecutionEmptyRequest;
import com.exercise.springjava.virtualThread.controller.response.ExecutionTimeResponse;
import com.exercise.springjava.virtualThread.controller.response.ThreadId;
import com.exercise.springjava.virtualThread.service.VirtualThreadExerciseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/virtual-thread")
public class VirtualThreadExerciseController {

    private final VirtualThreadExerciseService threadService;

    public VirtualThreadExerciseController(VirtualThreadExerciseService threadService) {
      this.threadService = threadService;
    }

    @GetMapping("/id")
    public ThreadId getVirtualThreadId() {
      threadService.printThreadId();
      return new ThreadId(Thread.currentThread().threadId());
    }

    // Execute empty task by virtual-thread
    @PutMapping("/virtual/empty")
    public ExecutionTimeResponse executeEmptyTaskByVirtualThread(@RequestBody ExecutionEmptyRequest request) {
      long start = System.currentTimeMillis();
      threadService.executeEmptyTaskByVirtualThread(request.getNum());
      return new ExecutionTimeResponse(System.currentTimeMillis() - start);
    }

    // Execute empty task by thread
    @PutMapping("/thread/empty")
    public ExecutionTimeResponse executeEmptyTaskByThread(@RequestBody ExecutionEmptyRequest request) {
      long start = System.currentTimeMillis();
      threadService.executeEmtpyTaskByThread(request.getNum());
      return new ExecutionTimeResponse(System.currentTimeMillis() - start);
    }

    // Execute Blocking task
    @GetMapping("/blocking")
    public ExecutionTimeResponse executeBlockingTask() throws InterruptedException {
      long start = System.currentTimeMillis();
      threadService.executeBlockingTask();
      return new ExecutionTimeResponse(System.currentTimeMillis() - start);
    }

    // Execute Empty task
    @GetMapping("/empty")
    public ExecutionTimeResponse executeEmptyTask() {
      long start = System.currentTimeMillis();
      threadService.executeEmptyTask();
      return new ExecutionTimeResponse(System.currentTimeMillis() - start);
    }
    
}

