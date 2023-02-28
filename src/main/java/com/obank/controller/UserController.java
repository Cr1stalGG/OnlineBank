package com.obank.controller;

import com.obank.entity.User;
import com.obank.repository.UserRepository;
import com.obank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    @Autowired
    public UserController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public String getAccount(@PathVariable Long id, Model model){
        User user = userRepository.getReferenceById(id);

        model.addAttribute("user", user);

        return "account";
    }

    @GetMapping("/add_card/{id}")
    public String getCardPage(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute("user",  userRepository.getReferenceById(id));

        return "card_creating";
    }

    @PostMapping("/create_card/{id}")
    public String cardCreator(
            @PathVariable Long id,
            @RequestParam("cardNumber") String cardNumber
    ){
        userService.updateCardNumber(id, cardNumber);

        return "redirect:/user/id/{id}";
    }
}
