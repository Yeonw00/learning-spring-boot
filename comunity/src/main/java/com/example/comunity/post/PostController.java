package com.example.comunity.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PostController {
	
	private PostService postService;
	
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@RequestMapping("say-hello-jsp")
	public String sayHelloJsp() {
		return "sayHello";
	}
	
	@RequestMapping("list-posts")
	public String listAllPosts(ModelMap model) {
		List<Post> posts = postService.findAllPosts();
		model.addAttribute("posts",posts);
		return "listPosts";
	}
	
	@RequestMapping("list-my-posts")
	public String listMyPosts(ModelMap model) {
		List<Post> posts = postService.findByUsername("Odung");
		model.addAttribute("posts", posts);
		return "listPosts";
	}
}
