package com.example.springsecurity5.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springsecurity5.repository.UserRepository;
import com.example.springsecurity5.util.Authority;

import lombok.RequiredArgsConstructor;

import com.example.springsecurity5.model.User;

@RequiredArgsConstructor
@Controller
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index(Authentication loginUser, Model model) {
        model.addAttribute("username", loginUser.getName());
        model.addAttribute("authority", loginUser.getAuthorities());
        return "user";
    }

    @GetMapping("/admin/list")
    public String showAdminList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String process(@Validated @ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // for (User listUser : userRepository.findAllUList()) {
        // if (listUser.getUserName().equals(user.getUserName())) {
        // return "register";
        // }
        // }

        if (user.isAdmin()) {
            user.setAuthority(Authority.ADMIN);
        } else {
            user.setAuthority(Authority.USER);
        }
        userRepository.save(user);

        return "redirect:/login?register";
    }
}
