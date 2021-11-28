package edu.rutgers.cs336.pages;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/session")
public class Session {
    @GetMapping
    public String index(Model model, HttpSession session) {
        model.addAttribute("value", session.getAttribute("value"));
        return "session";
    }

    @PostMapping
    public String update(@RequestParam String value, Model model, HttpSession session) {
        session.setAttribute("value", value);
        return index(model, session);
    }
}
