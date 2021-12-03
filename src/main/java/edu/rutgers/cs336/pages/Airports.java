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

import edu.rutgers.cs336.services.AirportSvc;
import edu.rutgers.cs336.services.AirportSvc.Airport;

@Controller
@RequestMapping("/airports")
public class Airports {
    @Autowired
    private AirportSvc airports;

    @GetMapping
    public String index (Model model) {
        model.addAttribute("airports", airports.index());
        return "airports";
    }

    @PostMapping
    public String create(@ModelAttribute Airport airport, Model model){
        airports.create(airport);
        return index(model);
    }

    @PutMapping
    public String update(@ModelAttribute Airport airport, Model model){
        airports.update(airport);
        return index (model);
    }

    @DeleteMapping
    public String delete(@ModelAttribute Airport airport, Model model) {
        airports.delete(airport.id());
        return index(model);
    }
}
