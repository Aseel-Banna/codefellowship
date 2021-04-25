package com.example.codefellowship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestParam(value="username") String username,
                               @RequestParam(value="password") String password,
                               @RequestParam(value = "firstName") String firstName,
                               @RequestParam(value = "lastName") String lastName,
                               @RequestParam(value = "dateOfBirth") String date,
                               @RequestParam(value = "bio") String bio){
        ApplicationUser newUser = new ApplicationUser(bCryptPasswordEncoder.encode(password), username,firstName, lastName,date,bio );
        newUser = applicationUserRepository.save(newUser);
        return new RedirectView("/login");
    }

    @PostMapping("/login")
    public RedirectView login(){
        return new RedirectView("/myprofile");
    }
//
//    @GetMapping("/myprofile")
//    public String getUserProfilePage(Principal p, Model m){
//        m.addAttribute("user", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
//        return "profile.html";
//    }

    @GetMapping("/users/{id}")
    public String getUserInfo(Principal p, Model m) {
        m.addAttribute("curUser", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
        return "profile.html";
    }

}