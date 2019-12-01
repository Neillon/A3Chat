package com.neillon.a3chat.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Chat implements Serializable {

    private String name;
    private String message;

    public Chat() {
    }

    public Chat(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return  "[NAME]: " + this.name + "\n" +
                "[MESSAGE]: " + this.message;
    }
}
