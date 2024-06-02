package com.example.multimodule.rest_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DemoControllerTest {

  private final DemoController demoController = new DemoController();

  @Test
  void testGetMessage() {
    String message = this.demoController.getMessage();
    assertEquals(message, "Hello World");
  }
}
