package ru.sbmongodb.exceptions;

public class TaskCollectionException extends Exception{
    private static final long serialVersionUId = 1L;

    public TaskCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id){
        return "Task with this id: " + id + " not found";
    }

    public static String TaskAlreadyExists(){
        return "Task with given title already exists";
    }
}
