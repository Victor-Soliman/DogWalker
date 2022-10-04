package main.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("general")
public class GeneralController {

    @GetMapping
    public String welcome(){
        return "Welcome to main page";
    }
}
