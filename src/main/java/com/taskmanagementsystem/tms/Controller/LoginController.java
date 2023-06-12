package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.LoginForm;
import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login-form")
    public String getLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        if(loginForm.getUsername().equals("admin") && loginForm.getPassword().equals("admin")) {
            return "redirect:/home";
        }
        model.addAttribute("invalidCredentials", true);

        return "login";
    }

}
