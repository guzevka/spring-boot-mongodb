package ru.sbmongodb.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import ru.sbmongodb.exceptions.TaskCollectionException;
import ru.sbmongodb.models.TaskDTO;
import ru.sbmongodb.repositories.TaskRepository;
import ru.sbmongodb.services.TaskService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<?> getAllTasks(){
        List<TaskDTO> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, tasks.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO task){
        try{
            taskService.createTask(task);
            return new ResponseEntity<TaskDTO>(task, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TaskCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<?> getSingleTask(@PathVariable("id") String id){
        try {
            return new ResponseEntity<>(taskService.getSingleTask(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("tasks/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TaskDTO task){
        try {
            taskService.updateTask(id, task);
            return new ResponseEntity<>("Update Task with this id: " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TaskCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id){
        try {
            taskService.deleteTaskById(id);
            return new ResponseEntity<>("successfully deleted with this id: " + id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
