package com.app.willywonkafactory.rest.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.willywonkafactory.rest.model.Worker;

public interface WorkerDao extends PagingAndSortingRepository<Worker, String> {

	public Worker findByName(String name);
}
