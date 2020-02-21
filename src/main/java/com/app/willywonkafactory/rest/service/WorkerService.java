package com.app.willywonkafactory.rest.service;

import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;

public interface WorkerService {

	public void createWorker(WorkerDto workerDto);

	public void editWorker(String id, WorkerDto workerDto) throws AppRuntimeException;

	public void getWorkers();

	public void getWorker();

}
