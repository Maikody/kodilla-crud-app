package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TaskMapper.class})
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void mapToTask() {
        TaskDto taskDto = new TaskDto(1L, "Java", "Read Effective Java");

        Task task = taskMapper.mapToTask(taskDto);

        assertEquals(1L, task.getId());
        assertEquals("Java", task.getTitle());
        assertEquals("Read Effective Java", task.getContent());
    }

    @Test
    void mapToTaskDto() {
        Task task = new Task(1L, "Java", "Read Effective Java");

        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        assertEquals(1L, taskDto.getId());
        assertEquals("Java", taskDto.getTitle());
        assertEquals("Read Effective Java", taskDto.getContent());
    }

    @Test
    void mapToTaskDtoList() {
        List<Task> tasks = getListOfTasks();

        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        assertEquals(1, taskDtos.size());
        assertEquals(1L, taskDtos.get(0).getId());
        assertEquals("Java", taskDtos.get(0).getTitle());
        assertEquals("Read Effective Java", taskDtos.get(0).getContent());
    }

    private List<Task> getListOfTasks() {
        Task task = new Task(1L, "Java", "Read Effective Java");
        return List.of(task);
    }
}