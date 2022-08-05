package com.mtn.assessment.commons;

import com.mtn.assessment.domain.Post;
import com.mtn.assessment.dto.PostDto;
import org.modelmapper.ModelMapper;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public final class PostWrapper {
    private static ModelMapper mapper = new ModelMapper();

    /**
     * convert entity to DTO
     * @param post
     * @return
     */
    public static PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    /**
     * convert DTO to entity
     * @param postDto
     * @return
     */
    public static Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
