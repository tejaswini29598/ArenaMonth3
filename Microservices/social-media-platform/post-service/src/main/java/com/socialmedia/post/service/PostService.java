package com.socialmedia.post.service;

import com.socialmedia.post.dto.PostDTO;
import com.socialmedia.post.entity.Post;
import com.socialmedia.post.event.PostCreatedEvent;
import com.socialmedia.post.repository.PostRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PostDTO createPost(Long userId, String content, String mediaUrls) {
        Post post = new Post();
        post.setUserId(userId);
        post.setContent(content);
        post.setMediaUrls(mediaUrls);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);

        // Publish event
        PostCreatedEvent event = new PostCreatedEvent(savedPost.getId(), userId, content);
        rabbitTemplate.convertAndSend("social-media.exchange", "post.created", event);

        return convertToDTO(savedPost);
    }

    public PostDTO getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToDTO);
    }

    public Page<PostDTO> getUserPosts(Long userId, Pageable pageable) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(this::convertToDTO);
    }

    public PostDTO updatePost(Long id, String content) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(content);
        post.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public PostDTO likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        if (!post.getLikedByUserIds().contains(userId)) {
            post.getLikedByUserIds().add(userId);
            post.setLikeCount(post.getLikeCount() + 1);
        }
        
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    public PostDTO unlikePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
        if (post.getLikedByUserIds().contains(userId)) {
            post.getLikedByUserIds().remove(userId);
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
        }
        
        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getContent(),
                post.getMediaUrls(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt().toString()
        );
    }
}
