package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.generic.BaseRepository;
import com.example.demo.generic.IBaseRepository;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User, Long> implements IUserRepository {

    public UserRepository(JinqJPAStreamProvider jinqProvider) {
        super(jinqProvider, User.class);
    }
}
