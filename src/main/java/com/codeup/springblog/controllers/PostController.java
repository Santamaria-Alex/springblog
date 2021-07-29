package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService){
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
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
    public String singlePost(@PathVariable long id, Model model) {
        Post post = postDao.getById(id);
        boolean isPostOwner = false;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            isPostOwner = currentUser.getId() == post.getUser().getId();
        }
        model.addAttribute("post", post);
        model.addAttribute("isPostOwner", isPostOwner);
        return "posts/show";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postDao.getById(id);
        if (currentUser.getId() == post.getId()) {
            model.addAttribute("post", postDao.getById(id));
            return "posts/edit";
        } else {
            return "redirect:/posts/" + id;
        }
    }

    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, @ModelAttribute Post post){
        post.setUser(userDao.getById(1L));
        postDao.save(post);
        return "redirect:/posts";
    }

    @PostMapping("/posts/delete/{id}")
    public String delete(@PathVariable long id, Model model){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        emailService.prepareAndSend(post, "Created new Post: " + post.getTitle(), "Your new post -" + post.getTitle() + "- reads: " + post.getBody());
        return "redirect:/posts";
    }



}
