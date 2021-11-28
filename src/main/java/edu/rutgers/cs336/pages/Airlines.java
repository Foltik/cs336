package edu.rutgers.cs336.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.rutgers.cs336.services.AirlineSvc;
import edu.rutgers.cs336.services.AirlineSvc.Airline;

@Controller
@RequestMapping("/airlines")
public class Airlines {
    @Autowired
    private AirlineSvc airlines;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("airlines", airlines.index());
        return "airlines";
    }

    @PostMapping
    public String create(@ModelAttribute Airline airline, Model model) {
        airlines.create(airline);
        return index(model);
    }

    @PutMapping
    public String update(@ModelAttribute Airline airline, Model model) {
        airlines.update(airline);
        return index(model);
    }

    @DeleteMapping
    public String delete(@ModelAttribute Airline airline, Model model) {
        airlines.delete(airline.id());
        return index(model);
    }
}
