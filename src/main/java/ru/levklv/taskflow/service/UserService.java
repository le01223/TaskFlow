package ru.levklv.taskflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.levklv.taskflow.entity.User;
import ru.levklv.taskflow.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> foundUser = userRepository.findById(id);

        return foundUser.orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);

        return foundUser.orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public boolean checkPasswords(User user, String password) {
        return user.getPassword().equals(password);
    }
}
