package com.example.demo.controller;

import com.example.demo.data.GridResult;
import com.example.demo.data.GridSearch;
import com.example.demo.entity.User;
import com.example.demo.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/userUrl")
    public String userUrl(Model model) {
        return "user";
    }

    /*@GetMapping("/getUsersList")
    public ResponseEntity<GridResult> getUsers(GridSearch gridSearch) {
        System.out.println(gridSearch);
        GridResult gridResult = userRepository.getGridResult(gridSearch);
        return new ResponseEntity<>(gridResult, HttpStatus.OK);
    }*/

    @PostMapping
    public String addUser(User user) {
        userRepository.save(user);
        return "redirect:/users/list";
    }
}
