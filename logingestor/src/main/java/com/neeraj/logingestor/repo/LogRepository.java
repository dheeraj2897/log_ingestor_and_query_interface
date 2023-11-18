package com.neeraj.logingestor.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neeraj.logingestor.entity.LogEntity;

@Repository
public interface LogRepository extends MongoRepository<LogEntity,String>{

}
