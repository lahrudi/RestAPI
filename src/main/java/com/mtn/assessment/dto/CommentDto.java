package com.mtn.assessment.dto;

import com.mtn.assessment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto implements Serializable {

    private Long postId;
    private Long id;
    private String name;
    private String email;
    private String body;

    public static CommentDto from(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setPostId(comment.getPostId());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "  postId=" + postId + '\'' +
                ", id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
