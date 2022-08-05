package com.mtn.assessment.exception;

import java.text.MessageFormat;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public class TodoNotFoundException extends RuntimeException
{
    public TodoNotFoundException(Long id) {
        super(MessageFormat.format("Could not find todo with id: {0}", id));
    }
}
