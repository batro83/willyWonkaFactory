package com.app.willywonkafactory.rest.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.willywonkafactory.rest.model.Worker;

public interface WorkerDao extends MongoRepository<Worker, String> {

}
