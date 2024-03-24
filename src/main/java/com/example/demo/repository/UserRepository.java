package com.example.demo.repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {
    List<Users> findAll();
    Users findByUsernameAndPassword(String Username,String password);

    Users findByToken(String token);
}
