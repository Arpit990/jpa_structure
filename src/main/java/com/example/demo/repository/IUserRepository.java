package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.generic.IBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends IBaseRepository<User, Long> {
}