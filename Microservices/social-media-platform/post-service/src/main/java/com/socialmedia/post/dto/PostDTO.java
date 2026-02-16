package com.socialmedia.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private Long userId;
    private String content;
    private String mediaUrls;
    private int likeCount;
    private int commentCount;
    private String createdAt;
}
