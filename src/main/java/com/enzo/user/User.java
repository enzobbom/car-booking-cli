package com.enzo.user;

import java.util.UUID;

/*
No setters as there are no top level edition methods.
Attributes that could eventually be editable:
    - 'name'
*/

public class User {
    private final UUID id;
    private final String name;

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name;
    }
}
