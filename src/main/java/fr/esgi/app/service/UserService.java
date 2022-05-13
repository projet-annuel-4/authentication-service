package fr.esgi.app.service;

import fr.esgi.app.domain.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);

    User findUserByEmail(String email);

    List<User> findAllUsers();

    User updateProfile(String email, User user);
}
