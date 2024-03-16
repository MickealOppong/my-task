package com.opp.todo.exceptions;

public class ActivityNotFoundException extends RuntimeException{

    public ActivityNotFoundException(String message){
        super(message);
    }

    public ActivityNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
