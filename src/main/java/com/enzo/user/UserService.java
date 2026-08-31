package com.enzo.user;

import java.util.UUID;

public class UserService {
    private final UserDao userDao = new UserDao();

    private static final String USER_NOT_FOUND_MSG = "User not found";

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUser(UUID userId) {
        User[] users = getUsers();

        if (users.length == 0) {
            throw new IllegalArgumentException(USER_NOT_FOUND_MSG);
        }

        User desiredUser = null;
        for (User user : users) {
            if (userId.equals(user.getId())) {
                desiredUser = user;
                break;
            }
        }

        if (desiredUser == null) {
            throw new IllegalArgumentException(USER_NOT_FOUND_MSG);
        }

        return desiredUser;
    }
}
