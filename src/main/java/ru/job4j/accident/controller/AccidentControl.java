package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.service.AccidentService;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AccidentControl {
    private final AccidentService service;

    public AccidentControl(AccidentService service) {
        this.service = service;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("types", service.findAllAccidentTypes());
        model.addAttribute("rules", service.findAllRules());
        return "accident/create";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("types", service.findAllAccidentTypes());
        model.addAttribute("rules", service.findAllRules());
        model.addAttribute("accident", service.findAccidentById(id));
        return "accident/edit";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids != null) {
            service.save(accident, ids);
        }
        return "redirect:/";
    }
}
