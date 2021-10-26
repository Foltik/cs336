package edu.rutgers.cs336.pages;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Hello {
    @GetMapping
    public String hello(Map<String, Object> model) {
        model.put("message", "Hello, world!");
        return "hello";
    }
}
