package ru.job4j.accident.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accident.model.User;
import ru.job4j.accident.service.AccidentService;

@Controller
public class RegControl {
    private final AccidentService service;

    public RegControl(@Qualifier("crudService") AccidentService service) {
        this.service = service;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        if (service.findUserByName(user.getUsername()) == null) {
            user.setEnabled(true);
            user.setPassword(service.getEncoder().encode(user.getPassword()));
            user.setAuthority(service.findByAuthority("ROLE_USER"));
            service.save(user);
            model.addAttribute("errorMessage", "User registered !!");
            return "/login";
        } else {
            model.addAttribute("errorMessage", "A user with the same name already exists !!");
            return "/reg";
        }
    }

    @GetMapping("/reg")
    public String regPage() {
        return "/reg";
    }
}
