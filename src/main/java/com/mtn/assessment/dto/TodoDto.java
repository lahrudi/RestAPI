package com.mtn.assessment.dto;

import com.mtn.assessment.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto implements Serializable {
    private Long id;
    private Long userId;
    private String title;
    private Boolean completed;

    public static TodoDto from(Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setUserId(todo.getUserId());
        todoDto.setTitle(todo.getTitle());
        todoDto.setCompleted(todo.getCompleted());
        return todoDto;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "  userId=" + userId + '\'' +
                ", id=" + id + '\'' +
                ", title='" + title + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}
