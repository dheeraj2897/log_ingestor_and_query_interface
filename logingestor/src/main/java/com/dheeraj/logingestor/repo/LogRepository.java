package com.dheeraj.logingestor.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dheeraj.logingestor.entity.LogEntity;

@Repository
public interface LogRepository extends MongoRepository<LogEntity,String>{

}
