package com.exercise.springjava.virtualThread.controller;

import org.springframework.web.bind.annotation.RestController;

import com.exercise.springjava.virtualThread.controller.response.ThreadId;
import com.exercise.springjava.virtualThread.service.VirtualThreadExerciseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;




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

    // Execute CPU bound task by virtual-thread

    // Execute IO bound task by virtual-thread

    // Execute empty task by thread

    // Execute CPU bound task by thread

    // Execute IO bound task by thread
}

