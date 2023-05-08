package ru.sbmongodb.services;

import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbmongodb.exceptions.TaskCollectionException;
import ru.sbmongodb.models.TaskDTO;
import ru.sbmongodb.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void createTask(TaskDTO task) throws TaskCollectionException, ConstraintViolationException {
        Optional<TaskDTO> taskOptional = taskRepository.findByTask(task.getTask());
        if(taskOptional.isPresent()){
            throw new TaskCollectionException(TaskCollectionException.TaskAlreadyExists());
        } else {
            task.setCreatedAt(new Date(System.currentTimeMillis()));
            taskRepository.save(task);
        }
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<TaskDTO> tasks = taskRepository.findAll();
        if(tasks.size() > 0) return tasks;
        else return new ArrayList<TaskDTO>();
    }

    @Override
    public TaskDTO getSingleTask(String id) throws TaskCollectionException {
        Optional<TaskDTO> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent())
            throw new TaskCollectionException(TaskCollectionException.NotFoundException(id));
        else
            return optionalTask.get();
    }

    @Override
    public void updateTask(String id, TaskDTO task) throws TaskCollectionException {
        Optional<TaskDTO> tasksWithId = taskRepository.findById(id);
        Optional<TaskDTO> taskWithSameName = taskRepository.findByTask(task.getTask());

        // присутствует этот объект или нет?
        if(tasksWithId.isPresent()) {
            if(taskWithSameName.isPresent() && taskWithSameName.get().getId().equals(id)) {
                throw new TaskCollectionException(TaskCollectionException.TaskAlreadyExists());
            }
            TaskDTO taskToUpdate = tasksWithId.get();
            taskToUpdate.setTask(task.getTask());
            taskToUpdate.setDescription(task.getDescription());
            taskToUpdate.setCompleted(taskToUpdate.isCompleted());
            taskToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            taskRepository.save(taskToUpdate);
        }
        else
            throw new TaskCollectionException(TaskCollectionException.NotFoundException(id));
    }

    @Override
    public void deleteTaskById(String id) throws TaskCollectionException {
        Optional<TaskDTO> taskOptional = taskRepository.findById(id);
        if(!taskOptional.isPresent())
            throw new TaskCollectionException(TaskCollectionException.NotFoundException(id));
        else
            taskRepository.deleteById(id);
    }
}
