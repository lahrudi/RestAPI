package com.mtn.assessment.exception;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class CommentNotFoundException extends RuntimeException
{
    public CommentNotFoundException(Long id) {
        super(MessageFormat.format("Could not find comment with id: {0}", id));
    }
}
