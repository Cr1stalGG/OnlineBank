package com.obank.controllers;

import com.obank.entity.User;
import com.obank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign")
public class SignController {

    private final UserRepository userRepository;

    @Autowired
    public SignController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/up")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());

        return("registration");
    }

    @PostMapping("/up_process")
    public String registrationAction(User user){
        userRepository.save(user);

        return "redirect:/user/id/1";
    }

    @GetMapping("/in")
    public String login() {
        return "login";
    }

}
