package com.obank.controller;

import com.obank.entity.Card;
import com.obank.entity.User;
import com.obank.repository.UserRepository;
import com.obank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        model.addAttribute("username", user.getUsername());
        model.addAttribute("cardNumber", user.getCard().getCardNumber());
        model.addAttribute("amount", user.getCard().getAmount());

        return "account";
    }

    @GetMapping("/add_card/{id}")
    public String getCardPage(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute("user",  userRepository.getReferenceById(id));
        model.addAttribute("id", id);

        return "card_creating";
    }

    @PostMapping("/create_card/{id}")
    public String cardCreator(
            @PathVariable Long id,
            @RequestParam("cardNumber") String cardNumber
    ){
        Pattern pattern = Pattern.compile("^[0-9]{16}$");
        Matcher matcher = pattern.matcher(cardNumber);
        if(matcher.matches())
            userService.updateCardNumber(id, cardNumber);
        else
            return "redirect:/user/add_card/{id}";
        return "redirect:/user/id/{id}";
    }
}
