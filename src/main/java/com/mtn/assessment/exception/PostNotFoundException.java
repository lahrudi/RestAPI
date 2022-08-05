package com.mtn.assessment.exception;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super(MessageFormat.format("Could not find post with id: {0}", id));
    }
}
