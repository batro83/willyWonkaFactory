package com.app.willywonkafactory.integration.unit.service;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dao.WorkerDao;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.service.impl.WorkerServiceImpl;

@RunWith(SpringRunner.class)
@FixMethodOrder(NAME_ASCENDING)
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

	@Test
	public void test004_getAllWorkersPaginate_ok() throws AppRuntimeException {
		Pageable pageableRequest = PageRequest.of(0, 2);
		List<Worker> workerList = newArrayList(new Worker(), new Worker());
		Page<Worker> pageWorker = new PageImpl<>(workerList, pageableRequest, 5);
		when(workerDao.findAll(any(PageRequest.class))).thenReturn(pageWorker);

		Page<Worker> response = workerService.getWorkers(pageableRequest);

		verify(workerDao).findAll(any(PageRequest.class));
		assertEquals(5, response.getTotalElements());
		assertEquals(0, response.getPageable().getPageNumber());
		assertEquals(2, response.getPageable().getPageSize());
		assertEquals(2, response.getContent().size());
	}

}