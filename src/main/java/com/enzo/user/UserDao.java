package com.enzo.user;

public class UserDao {
    private static final User[] users;
    static {
        users = new User[]{
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
        return users;
    }
}
