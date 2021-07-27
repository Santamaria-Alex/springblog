package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.models.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao){
        this.postDao = postDao;
        this.userDao = userDao;
    }

//    List<Post> posts = new ArrayList<>();

    @GetMapping("/posts")
    public String viewPosts(Model model){
//        posts.add(new Post("This is post1", "This is post1s body"));
//        posts.add(new Post("This is post2", "This is post2s body"));
        model.addAttribute("posts", postDao.findAll());
        return "posts/index";

    }

    @GetMapping("/posts/{id}")
    public String singlePost(@PathVariable long id, Model model){
        model.addAttribute("post", postDao.getById(id));
        return "posts/show";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model){
        model.addAttribute("post", postDao.getById(id));
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @ModelAttribute Post post){
        post.setUser(userDao.getById(1L));
        postDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/delete/{id}")
    public String delete(@PathVariable long id, Model model){
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    //when you visit the url you will see the form to create a post.
    @GetMapping("/posts/create")
    public String createForm(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    //when you submit the form on the /post/create page,
    //the information will be posted to the same URL
//    @RequestMapping(path = "/posts/create", method = RequestMethod.POST)
    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post){
        User user = userDao.getById(1L);
        post.setUser(user);
        postDao.save(post);
        return "redirect:/posts";
    }



}
