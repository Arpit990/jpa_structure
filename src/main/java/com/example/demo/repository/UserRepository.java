package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.generic.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository extends BaseRepository<User, UUID> implements IUserRepository {

    public UserRepository() {
        super(User.class);
    }
}
