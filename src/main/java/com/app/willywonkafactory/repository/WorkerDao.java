package com.app.willywonkafactory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.willywonkafactory.model.Worker;

public interface WorkerDao extends MongoRepository<Worker, String> {

}
