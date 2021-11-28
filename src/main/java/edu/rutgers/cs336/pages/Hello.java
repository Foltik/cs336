package edu.rutgers.cs336.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Hello {
    @GetMapping
    public String hello(Model model) {
        model.addAttribute("message", "Hello, world!");
        return "hello";
    }
}
