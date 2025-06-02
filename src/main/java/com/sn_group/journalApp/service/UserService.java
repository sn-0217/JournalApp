package com.sn_group.journalApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sn_group.journalApp.entity.User;
import com.sn_group.journalApp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public void saveAllEntries(List<User> users){
        userRepository.saveAll(users);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void deleteById(String userName){
        userRepository.deleteByUserName(userName);
    }
}
