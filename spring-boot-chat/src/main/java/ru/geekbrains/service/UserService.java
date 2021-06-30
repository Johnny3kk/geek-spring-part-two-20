package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Set<String> onlineUsers;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.onlineUsers = new ConcurrentSkipListSet<>();
    }

    public void create(UserFormRepr userFormRepr) {
        User user = new User();
        user.setName(userFormRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userFormRepr.getPassword()));
        repository.save(user);
    }

    public List<UserRepr> findAllUsers() {
        return repository.findAll().stream()
                .map(UserRepr::new)
                .map(usr -> usr.withOnline(onlineUsers.contains(usr.getUsername())))
                .collect(Collectors.toList());
    }

    public UserRepr findByUsername(String username) {
        UserRepr foundUser =  new UserRepr(repository.findUserByName(username));
        foundUser.withOnline(onlineUsers.contains(foundUser.getUsername()));
        return foundUser;
    }

    public void setUserOnline(String username) {
        onlineUsers.add(username);
    }

    public void setUserOffline(String username) {
        onlineUsers.remove(username);
    }

}
