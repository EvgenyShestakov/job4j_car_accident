package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", Arrays.asList("John", "Mike", "Tony"));
        return "index";
    }
}
