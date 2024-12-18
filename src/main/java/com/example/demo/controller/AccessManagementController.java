package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/module")
public class AccessManagementController {
    private final IUserRepository userRepository;

    public AccessManagementController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<User> users = userRepository.getAll();
        model.addAttribute("users", users);
        return "access-management/module";
    }

    @PostMapping
    public String addUser (User user) {
        userRepository.save(user);
        return "redirect:/users/list";
    }
}
