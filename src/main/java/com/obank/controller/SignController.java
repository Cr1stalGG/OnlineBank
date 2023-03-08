package com.obank.controller;

import com.obank.entity.User;
import com.obank.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/sign")
public class SignController {

    private final UserService userService;

    @Autowired
    public SignController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/up")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());

        return("registration");
    }

    @GetMapping("/in")
    public String login(){
        return "login";
    }

    @GetMapping("/login-success")
    public void getLoginInfo(
            @AuthenticationPrincipal UserDetails authentication,
            HttpServletResponse response
    ) throws IOException {
        User user = userService.getUserByUsername(authentication.getUsername());

        response.sendRedirect("/user/id/" + user.getId());
    }

    @PostMapping("/up_process")
    public String registrationAction(
            User user,
            @RequestParam("password") String password,
            @RequestParam("confPassword") String confPassword)
    {
        if(password.equals(confPassword)) {
            userService.save(user);

            return "redirect:/user/id/" + user.getId().toString();
        }else
            return "redirect:/sign/up";
    }

}
