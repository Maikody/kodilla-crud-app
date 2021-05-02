package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({DbService.class})
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldSaveTaskInRepository() {
        Task task = new Task(1L, "Test", "Testing");

        dbService.saveTask(task);

        assertThat(taskRepository.findAll()).hasSize(1);
    }

    @Test
    void shouldGetAllTasks() {
        Task task = new Task(1L, "Test", "Testing");

        taskRepository.save(task);

        assertThat(dbService.getAllTasks()).hasSize(1);
    }

    @Test
    void shouldGetTaskById() {
        Task task = new Task(1L, "Test", "Testing");

        taskRepository.save(task);

        Task taskFromRepo = dbService.getTask(1L).get();

        assertThat(taskFromRepo).isNotNull();
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task(1L, "Test", "Testing");

        taskRepository.save(task);

        dbService.deleteTask(1L);

        assertThat(taskRepository.findAll()).isEmpty();
    }
}