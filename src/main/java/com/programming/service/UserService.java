package com.programming.service;

import com.programming.custom_exception.ResourceNotFoundException;
import com.programming.model.User;
import com.programming.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "users",key = "#id")
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found with given id : "+id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @CachePut(value = "users",key = "#user.id")
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @CacheEvict(value = "users",key = "#id")
    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "Record deleted with given id : "+id;
    }

}
