package com.obank.controller;

import com.obank.entity.User;
import com.obank.repository.UserRepository;
import com.obank.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    public String getAccount(
            @PathVariable Long id,
            Model model
    ){
        User user = userRepository.getReferenceByUsername(userService.getCurrentUsername());

        if(!user.getId().equals(id))
            return "redirect:/user/id/"+user.getId();

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

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null)
            new SecurityContextLogoutHandler().logout(request, response, auth);

        return "redirect:/";
    }
}
