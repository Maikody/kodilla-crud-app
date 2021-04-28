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

import static org.assertj.core.api.Assertions.assertThat;
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

        assertThat(task).extracting("id").containsExactly(1L);
        assertThat(task).extracting("title").containsExactly("Java");
        assertThat(task).extracting("content").containsExactly("Read Effective Java");
    }

    @Test
    void mapToTaskDto() {
        Task task = new Task(1L, "Java", "Read Effective Java");

        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        assertThat(taskDto).extracting("id").containsExactly(1L);
        assertThat(taskDto).extracting("title").containsExactly("Java");
        assertThat(taskDto).extracting("content").containsExactly("Read Effective Java");
    }

    @Test
    void mapToTaskDtoList() {
        List<Task> tasks = getListOfTasks();

        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        assertThat(taskDtos).hasSize(1);
        assertThat(taskDtos).extracting("id").containsOnly(1L);
        assertThat(taskDtos).extracting("title").containsOnly("Java");
        assertThat(taskDtos).extracting("content").containsOnly("Read Effective Java");
    }

    private List<Task> getListOfTasks() {
        Task task = new Task(1L, "Java", "Read Effective Java");
        return List.of(task);
    }
}