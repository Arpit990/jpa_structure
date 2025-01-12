package com.example.demo.repository;

import com.example.demo.entity.DesignationMaster;
import com.example.demo.generic.BaseRepository;
import org.jinq.jpa.JinqJPAStreamProvider;
import org.springframework.stereotype.Repository;

@Repository
public class DesignationRepository extends BaseRepository<DesignationMaster, Long> implements IDesignationRepository {

    DesignationRepository(JinqJPAStreamProvider jinqProvider) {
        super(jinqProvider, DesignationMaster.class);
    }
}
