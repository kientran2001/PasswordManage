package com.example.PasswordManage.controllers;

import com.example.PasswordManage.models.LoginForm;
import com.example.PasswordManage.models.RegisterForm;
import com.example.PasswordManage.models.User;
import com.example.PasswordManage.repositories.UserRepository;
import com.example.PasswordManage.services.HmacService;
import com.example.PasswordManage.services.KeyService;
import com.example.PasswordManage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Optional;


@Controller
@RequestMapping(path = "")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        modelMap.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    @PostMapping("/login")
    public String login(ModelMap modelMap,
                        @Valid @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "login";
        }

        try {
            String username = loginForm.getUsername().trim();
            Optional<User> user = userRepository.findByUsername(username);

            if(user.isEmpty()) {
                System.out.println("User not found!");
                return "login";
            } else {
                String password = loginForm.getPassword().trim();
                String salt = user.get().getSalt();
                String hmac = user.get().getHmac();
                String calHmac = HmacService.calculateHMAC(password, salt);
                if(hmac.equals(calHmac)) {
                    int userId = user.get().getId();
                    return "redirect:account/allAccounts/" + userId + "." + password + "." + hmac;
                } else {
                    System.out.println("Password is incorrect!");
                    return "login";
                }
            }
        }catch (Exception e) {
            modelMap.addAttribute("error", e.toString());
            return "login";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        modelMap.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(ModelMap modelMap,
                           @Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "login";
        }

        try {
            String username = registerForm.getUsername().trim();
            String password = registerForm.getPassword().trim();
            String confirmPassword = registerForm.getConfirmPassword().trim();

            Optional<User> user = userRepository.findByUsername(username);
            if(!user.isEmpty()) {
                modelMap.addAttribute("error", "User already exist!");
            }

            if(!password.equals(confirmPassword)) {
                modelMap.addAttribute("error", "Confirm password and password must be same");
                System.out.println("Confirm password and password must be same");
                return "redirect:/register";
            } else {
                User savedUser = userService.createUser(username, password);
                if(savedUser.getId() > 0) {
                    SecretKey seed = KeyService.createSeed();
                    String ksFile = "ks" + username + ".keystore";
                    KeyService.storeSeedToKeyStore(seed, password, ksFile);
                    System.out.println("Register successfully for user has username: " + username);
                    return "redirect:/login";
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "redirect:/register";
        }
        return "redirect";
    }
}
