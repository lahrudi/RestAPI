package com.mtn.assessment.commons;

import com.mtn.assessment.domain.Todo;
import com.mtn.assessment.dto.TodoDto;
import org.modelmapper.ModelMapper;

/**
 * @author Alireza Gholamzadeh Lahroodi
 */
public final class TodoWrapper {
    private static ModelMapper mapper = new ModelMapper();

    /**
     * convert entity to DTO
     * @param todo
     * @return
     */
    public static TodoDto mapToDTO(Todo todo) {
        TodoDto todoDto = mapper.map(todo, TodoDto.class);
        return todoDto;
    }

    /**
     * convert DTO to entity
     * @param todoDto
     * @return
     */
    public static Todo mapToEntity(TodoDto todoDto) {
        Todo todo = mapper.map(todoDto, Todo.class);
        return todo;
    }
}
