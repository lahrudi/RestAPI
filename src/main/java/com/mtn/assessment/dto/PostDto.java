package com.mtn.assessment.dto;

import com.mtn.assessment.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String body;

    public static PostDto from(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setUserId(post.getUserId());
        postDto.setTitle(post.getTitle());
        postDto.setBody(post.getBody());
        return postDto;
    }

    @Override
    public String toString() {
        return "Post{" +
                "  userId=" + userId + '\'' +
                ", id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
