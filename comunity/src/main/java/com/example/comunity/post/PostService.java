package com.example.comunity.post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PostService {
	private static List<Post> posts = new ArrayList();
	
	static {
		posts.add(new Post(1, "Odung", "Hi, everyone. I'm Odung", 
							LocalDate.now(), false));
		posts.add(new Post(2, "Yeonwoo", "Hi Odung! I love you", 
				LocalDate.now(), true));
		posts.add(new Post(3, "Younghwan", "Hey! Odung is mine", 
				LocalDate.now(), false));
	}
	
	public List<Post> findByUsername(String username) {
		List<Post> myPosts = new ArrayList();
		for(Post post: posts) {
			if(post.getUsername().equals(username)) {
				myPosts.add(post);
			}
		}
		return myPosts;
	}
	
	public List<Post> findAllPosts() {
		return posts;
	}
}
