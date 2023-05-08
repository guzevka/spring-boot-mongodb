package ru.sbmongodb.services;

import jakarta.validation.ConstraintViolationException;
import ru.sbmongodb.exceptions.TaskCollectionException;
import ru.sbmongodb.models.TaskDTO;

import java.util.List;

public interface TaskService {

    public void createTask(TaskDTO task) throws TaskCollectionException, ConstraintViolationException;

    public List<TaskDTO> getAllTasks();

    public TaskDTO getSingleTask(String id) throws TaskCollectionException;

    public void updateTask(String id, TaskDTO task) throws TaskCollectionException;

    public void deleteTaskById(String id) throws TaskCollectionException;

}
