package com.example.spring_virtual_thread.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_virtual_thread.controller.request.ExecutionEmptyRequest;
import com.example.spring_virtual_thread.controller.response.ExecutionTimeResponse;
import com.example.spring_virtual_thread.service.VirtualThreadService;

@RestController
@RequestMapping("/virtual-thread")
public class VirtualThreadController {
  private final VirtualThreadService vtService;

  public VirtualThreadController(VirtualThreadService vtService) {
    this.vtService = vtService;
  }

  @PutMapping("/id")
  public void getVirtualThreadId() {
    vtService.printThreadId();
    return ;
  }

  // Execute empty task by virtual-thread
  @PutMapping("/virtual/empty")
  public ExecutionTimeResponse executeEmptyTaskByVirtualThread(@RequestBody ExecutionEmptyRequest request) {
    long start = System.currentTimeMillis();
    vtService.executeEmptyTaskByVirtualThread(request.getNum());
    return new ExecutionTimeResponse(System.currentTimeMillis() - start);
  }

  // Execute empty task by thread
  @PutMapping("/thread/empty")
  public ExecutionTimeResponse executeEmptyTaskByThread(@RequestBody ExecutionEmptyRequest request) {
    long start = System.currentTimeMillis();
    vtService.executeEmtpyTaskByThread(request.getNum());
    return new ExecutionTimeResponse(System.currentTimeMillis() - start);
  }

  // Execute Blocking task
  @GetMapping("/blocking")
  public ExecutionTimeResponse executeBlockingTask() throws InterruptedException {
    long start = System.currentTimeMillis();
    vtService.executeBlockingTask();
    return new ExecutionTimeResponse(System.currentTimeMillis() - start);
  }

  // Execute Empty task
  @GetMapping("/empty")
  public ExecutionTimeResponse executeEmptyTask() {
    long start = System.currentTimeMillis();
    vtService.executeEmptyTask();
    return new ExecutionTimeResponse(System.currentTimeMillis() - start);
  }
}
