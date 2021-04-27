package com.example.codefellowship;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserFollowersRepository usersFollowersRepository;

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

//    @GetMapping("/users/{id}")
//    public String getUserInfo(Principal p, Model m) {
//        m.addAttribute("curUser", ((UsernamePasswordAuthenticationToken)p).getPrincipal());
//        return "profile.html";
//    }




    @GetMapping("/users/{id}")
    public String getUserInfo(Principal p, Model m, @PathVariable(value = "id") int id) {
        if(((UsernamePasswordAuthenticationToken) p) != null){
            ApplicationUser userDetails =(ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
            m.addAttribute("username", userDetails.getUsername());
        }
        System.out.println("helllo");
        ApplicationUser applicationUser =(ApplicationUser) applicationUserRepository.findById(id).get();
        if(applicationUser != null){
            m.addAttribute("curUser", applicationUser);
            return "userprofile";
        }
        return "/error?message=You%are%not%allow%to%delete%the%user";
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public String handleGetAllUsersData(Model m,Principal p) {
        m.addAttribute("users", applicationUserRepository.findAll());
        return "allUsers";
    }

    @RequestMapping(value = "/follow/{id}", method = RequestMethod.GET)
    public String handleFollowUser(Model m, @PathVariable(value = "id") Integer id,Principal p) {
        if(((UsernamePasswordAuthenticationToken) p) != null){
            ApplicationUser userDetails =(ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
            ApplicationUser applicationUser = applicationUserRepository.findById(userDetails.getId()).get();
            ApplicationUser followedUser = applicationUserRepository.findById(id).get();
            System.out.println();
            usersFollowersRepository.save(new UserFollowers(applicationUser,followedUser));
            System.out.println(usersFollowersRepository.findAll());
        }
//        m.addAttribute("users", applicationUserRepository.findAll());
        return "allUsers";
    }



//        m.addAttribute("followings", follows);


    @RequestMapping(value = "/feed", method = RequestMethod.GET)
    public String handleFeed(Model m,Principal p) {
        if(((UsernamePasswordAuthenticationToken) p) != null){
            ApplicationUser userDetails =(ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
            ApplicationUser applicationUser = applicationUserRepository.findById(userDetails.getId()).get();
            ApplicationUser currentUser = applicationUserRepository.findByUsername(p.getName());
            ArrayList allFollowerPosts = new ArrayList();
            Set<UserFollowers> allFollower  =  applicationUser.getUsers();
            for (UserFollowers user:  allFollower){
                allFollowerPosts.addAll(user.getApplicationUserFollower().getPosts());
            }
//            m.addAttribute("users", userDetails);
            m.addAttribute("currentUser", currentUser);
            m.addAttribute("posts",allFollowerPosts);
            System.out.println(allFollowerPosts);
        }
        return "posts";
    }


}
