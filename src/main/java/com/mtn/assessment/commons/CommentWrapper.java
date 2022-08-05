package com.mtn.assessment.commons;

import com.mtn.assessment.domain.Comment;
import com.mtn.assessment.dto.CommentDto;
import org.modelmapper.ModelMapper;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public final class CommentWrapper {
    private static ModelMapper mapper = new ModelMapper();

    /**
     * convert entity to DTO
     * @param comment
     * @return
     */
    public static CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }

    /**
     * convert DTO to entity
     * @param commentDto
     * @return
     */
    public static Comment mapToEntity(CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        return comment;
    }
}
