package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/task")
public class TaskController {

    private TaskMapper taskMapper;
    private DbService dbService;

    public TaskController(TaskMapper taskMapper, DbService dbService) {
        this.taskMapper = taskMapper;
        this.dbService = dbService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(Long taskId) {
        Task foundTask = dbService.getTaskById(taskId);
        return taskMapper.mapToTaskDto(foundTask);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public void deleteTask(Long taskId){

    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateTask")
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test_content");
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTask")
    public void createTask(TaskDto taskDto) {

    }
}
