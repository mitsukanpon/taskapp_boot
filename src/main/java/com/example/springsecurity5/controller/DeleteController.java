package com.example.springsecurity5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity5.model.Task;
import com.example.springsecurity5.repository.TaskRepository;

@RestController
@RequestMapping("/api/task")
public class DeleteController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/delete")
    public ResponseEntity<String> deleteTask(@RequestBody List<Task> deleteTaskList) {

        try {
            for (Task task : deleteTaskList) {
                taskRepository.delete(task);
            }
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}
