package com.codeup.springblog.controllers;

import com.codeup.springblog.services.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {
    private final EmailService emailService;

    public HelloController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "<h1>Hello from Spring!</h1>";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/join")
    public String showJoinForm(){
        return "join";
    }

    //name inside @RequestParam must be the same as the name value inside the input, or wherever you're wanting to pass this method
    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model){
        model.addAttribute("cohort", "Welcome to " + cohort + "!");
//        emailService.prepareAndSend("test@test.com", "Hello, welcome.", "thanks a bunch!");
        return "join"; //returns everything back into the HTML
    }



    @GetMapping("/number/{num}")
    @ResponseBody
    public int displayNumber(@PathVariable int num){
        return num;
    }

    @RequestMapping(path = "hello/in/{color}", method = RequestMethod.GET)
    @ResponseBody
    public String helloInColor(@PathVariable String color){
        return "<h1 style=\"color: " + color + "\">Hello in " + color + "!</h1>";
    }
}
