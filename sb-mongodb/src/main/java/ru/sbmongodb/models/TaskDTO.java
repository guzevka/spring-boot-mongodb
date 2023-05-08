package ru.sbmongodb.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class TaskDTO {
    @Id
    private String id;
    @NotNull(message = "task can't be null")
    private String task;
    @NotNull(message = "description can't be null")
    private String description;
    @NotNull(message = "completed can't be null")
    private boolean completed;
    private Date createdAt;
    private Date updatedAt;

}
