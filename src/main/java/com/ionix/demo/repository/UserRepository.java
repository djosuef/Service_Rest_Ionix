package com.ionix.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ionix.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    public List<User> findByEmail(String email);

    public void deleteById(Long id);

    public boolean existsById(Long id);

}
