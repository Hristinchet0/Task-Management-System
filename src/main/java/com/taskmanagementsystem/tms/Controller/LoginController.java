package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        if (loginForm.getUsername().equals("admin") && loginForm.getPassword().equals("admin")) {
            return "redirect:/home";
        }
        model.addAttribute("invalidCredentials", true);

        return "login";
    }
}




