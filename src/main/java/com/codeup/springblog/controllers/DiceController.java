package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class DiceController {
    private static Random random = new Random();

    @GetMapping("/roll-dice")
    public String rollDice(){
        return "dice";
    }

    @GetMapping("/roll-dice/{guess}")
    public String rolledDice(@PathVariable String guess, Model model){
        model.addAttribute("guess", guess);
        int roll = random.nextInt(6) +1;
        model.addAttribute("roll", roll);
        return "dice";
    }
}
