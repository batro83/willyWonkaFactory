package com.app.willywonkafactory.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;

public interface WorkerService {

	public void createWorker(WorkerDto workerDto);

	public void editWorker(String id, WorkerDto workerDto) throws AppRuntimeException;

	public Page<Worker> getWorkers(Pageable page);

	public Worker getWorker(String id) throws AppRuntimeException;

}
