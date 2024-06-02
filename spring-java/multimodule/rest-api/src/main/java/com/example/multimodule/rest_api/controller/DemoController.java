package com.example.multimodule.rest_api.controller;

import org.springframework.stereotype.Controller;

@Controller
public class DemoController {
  public String getMessage() {
    return "Hello World";
  }
}
