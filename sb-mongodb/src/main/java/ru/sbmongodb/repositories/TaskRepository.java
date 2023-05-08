package ru.sbmongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sbmongodb.models.TaskDTO;

import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<TaskDTO, String> {

    // чтобы избежать дубликаты
    @Query("{'task': ?0}")
    Optional<TaskDTO> findByTask(String task);

}
