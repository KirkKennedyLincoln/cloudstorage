package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;

@Service
public class UserService {

    private final HashService hashService;

    private UserMapper mapper;
    private User user;

    public UserService(UserMapper userMapper, EncryptionService encryptionService, HashService hashService) {
        this.mapper = userMapper;
        this.hashService = hashService;
    }

    public Boolean createUser(User user) {
        User found = this.getUserByUsername(user.getUsername());
        if (null == found) {
            return this.setUser(user);
        }

        return false;
    }

    public User getUserByUsername(String username) {
        return mapper.getUserByUsername(username);
    }

    public User getUser() {
        return user;
    }

    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }
    
    public Boolean setUser(User user) {
        String salt = hashService.generateSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), salt);

        User newUser = new User(
            null,
            user.getUsername(),
            salt,
            hashedPassword,
            user.getFirstName(),
            user.getLastName()
        );

        Boolean inserted = this.mapper.insertUser(newUser);
        return inserted;
    }
}
