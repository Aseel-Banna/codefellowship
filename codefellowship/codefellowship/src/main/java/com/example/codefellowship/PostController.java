package com.example.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;	import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;	import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;	import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;


import java.security.Principal;
import java.sql.Timestamp;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository appUserRepository;

    @GetMapping("/myprofile")
    public String getLoggedProfile(Principal p, Model m) {

        //Get logged user info from db
        ApplicationUser me = appUserRepository.findByUsername(p.getName());

        //Get list of posts
        Iterable<Post> posts = me.getPosts();
        m.addAttribute("curUser", me);
        m.addAttribute("posts", posts);

        return "profile.html";
    }

    @GetMapping("/posts/add")
    public String getDinosaurCreator(Principal p, Model m) {
        ApplicationUser me = appUserRepository.findByUsername(p.getName());

        m.addAttribute("curUser", me);
        return "createpost";
    }

    @PostMapping("/posts/add")
    public String addPost(String body, Principal p) {
        ApplicationUser me = appUserRepository.findByUsername(p.getName());
        Post newPost = new Post(body, me);
        postRepository.save(newPost);
        return "redirect:/myprofile";
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    class DinosaurDoesNotBelongToYouException extends RuntimeException {
        public DinosaurDoesNotBelongToYouException(String s) {
            super(s);
        }
    }

}
