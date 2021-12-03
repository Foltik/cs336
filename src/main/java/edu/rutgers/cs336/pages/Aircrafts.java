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

import edu.rutgers.cs336.services.AircraftSvc;
import edu.rutgers.cs336.services.AircraftSvc.Aircraft;

@Controller
@RequestMapping("/aircrafts")
public class Aircrafts {
    @Autowired
    private AircraftSvc aircrafts;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("aircrafts", aircrafts.index());
        return "aircrafts";
    }

    @PostMapping
    public String create(@ModelAttribute Aircraft aircraft, Model model) {
        aircrafts.create(aircraft);
        return index(model);
    }

    @PutMapping
    public String update(@ModelAttribute Aircraft aircraft, Model model) {
        aircrafts.update(aircraft);
        return index(model);
    }

    @DeleteMapping
    public String delete(@ModelAttribute Aircraft aircraft, Model model) {
        aircrafts.delete(aircraft.id());
        return index(model);
    }
}



