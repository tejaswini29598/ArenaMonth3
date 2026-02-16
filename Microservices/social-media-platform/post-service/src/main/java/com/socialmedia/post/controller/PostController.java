package com.socialmedia.post.controller;

import com.socialmedia.post.dto.PostDTO;
import com.socialmedia.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> createPost(
            @RequestParam Long userId,
            @RequestParam String content,
            @RequestParam(required = false) String mediaUrls) {
        PostDTO post = postService.createPost(userId, content, mediaUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PostDTO>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getAllPosts(PageRequest.of(page, size)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostDTO>> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(postService.getUserPosts(userId, PageRequest.of(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestParam String content) {
        return ResponseEntity.ok(postService.updatePost(id, content));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<PostDTO> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.likePost(postId, userId));
    }

    @PostMapping("/{postId}/unlike")
    public ResponseEntity<PostDTO> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(postService.unlikePost(postId, userId));
    }
}
