package edu.rutgers.cs336.pages;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Hello {
    @GetMapping
    public String hello(HttpSession session,Model model) {
        model.addAttribute("user", session.getAttribute("user"));
        return "hello";
    }
}
