package com.app.willywonkafactory.rest.service.impl;

import static com.app.willywonkafactory.rest.exception.ErrorCode.NO_WORKER_FOUND;
import static java.util.Optional.ofNullable;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.willywonkafactory.rest.dao.WorkerDao;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao workerDao;
	@Autowired
	private ModelMapper mapper;

	@Override
	public void createWorker(WorkerDto workerDto) {
		Worker map = mapper.map(workerDto, Worker.class);
		workerDao.save(map);
	}

	@Override
	public void editWorker(String id, WorkerDto workerDto) throws AppRuntimeException {
		Worker workerUpdate = getWorker(id);
		ofNullable(workerDto.getAge()).ifPresent(workerUpdate::setAge);
		ofNullable(workerDto.getDescription()).ifPresent(workerUpdate::setDescription);
		ofNullable(workerDto.getHeight()).ifPresent(workerUpdate::setHeight);
		ofNullable(workerDto.getJob()).ifPresent(workerUpdate::setJob);
		ofNullable(workerDto.getName()).ifPresent(workerUpdate::setName);
		ofNullable(workerDto.getWeight()).ifPresent(workerUpdate::setWeight);

		workerDao.save(workerUpdate);
	}

	@Override
	public Page<Worker> getWorkers(Pageable page) {
		return workerDao.findAll(page);
	}

	@Override
	public Worker getWorker(String id) throws AppRuntimeException {
		return workerDao.findById(id)
				.orElseThrow(() -> new AppRuntimeException(SC_BAD_REQUEST, NO_WORKER_FOUND,
						"There is no worker with id ".concat(id)));
	}
}