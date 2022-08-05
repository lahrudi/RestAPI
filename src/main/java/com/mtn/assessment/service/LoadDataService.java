package com.mtn.assessment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadDataService {

    private final PostService postService;
    private final CommentService commentService;
    private final TodoService todoService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws IOException {
        String url = "https://jsonplaceholder.typicode.com/posts";
        postService.loadPostsData(url);
        log.info("The posts data is loaded.");

        url = "https://jsonplaceholder.typicode.com/comments";
        commentService.loadCommentsData(url);
        log.info("The comments data is loaded.");

        url = "https://jsonplaceholder.typicode.com/todos";
        todoService.loadTodosData(url);
        log.info("The todos data is loaded.");
    }
}
