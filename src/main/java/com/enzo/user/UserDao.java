package com.enzo.user;

import java.util.UUID;

public class UserDao {
    private static final User[] USERS;
    static {
        USERS = new User[]{
                new User("James"),
                new User("Jamila"),
                new User("George"),
                new User("Jack"),
                new User("Ana"),
                new User("Carl"),
                new User("Maxime"),
                new User("Roberta"),
                new User("Pablo"),
        };
    }

    public User[] getUsers() {
        return USERS;
    }
}
