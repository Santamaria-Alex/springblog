package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    @GetMapping("/posts")
    @ResponseBody
    public String viewPosts(){
        return "View all posts.";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String singlePost(@PathVariable long id){
        return "View an individual post.";
    }

    //when you visit the url you will see the form to create a post.
    @GetMapping("/posts/create")
    @ResponseBody
    public String createForm(){
        return "View form to create a post.";
    }

    //when you submit the form on the /post/create page,
    //the information will be posted to the same URL
//    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    @PostMapping("/posts/create")
    @ResponseBody
    public String createPost(){
        return "Create new post.";
    }



}
