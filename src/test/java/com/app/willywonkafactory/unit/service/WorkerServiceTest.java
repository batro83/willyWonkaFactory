package com.app.willywonkafactory.unit.service;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dao.WorkerDao;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.service.impl.WorkerServiceImpl;

@RunWith(SpringRunner.class)
public class WorkerServiceTest {

	@Mock
	private WorkerDao workerDao;

	@Mock
	private ModelMapper mapper;

	@InjectMocks
	private WorkerServiceImpl workerService;

	@Test
	public void test001_addWorker_ok() {
		when(mapper.map(any(), any())).thenReturn(new Worker());
		workerService.createWorker(new WorkerDto());
		verify(workerDao).save(any(Worker.class));
	}

	@Test(expected = AppRuntimeException.class)
	public void test002_editWorker_ko() throws AppRuntimeException {
		when(workerDao.findById(any())).thenReturn(empty());
		workerService.editWorker("id", new WorkerDto());
	}

	@Test
	public void test003_editWorker_ok() throws AppRuntimeException {
		when(workerDao.findById(any())).thenReturn(of(new Worker()));
		workerService.editWorker("id", new WorkerDto());
		verify(workerDao).save(any(Worker.class));
		verify(workerDao).findById(any());
	}

}
