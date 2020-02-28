package com.app.willywonkafactory.integration.unit.controller;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.controller.WorkerController;
import com.app.willywonkafactory.rest.dto.BaseWorkerDto;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.pagination.RestResponsePage;
import com.app.willywonkafactory.rest.service.impl.WorkerServiceImpl;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WorkerControllerTest {

	@InjectMocks
	private WorkerController workerController;

	@Mock
	private WorkerServiceImpl workerService;

	@Mock
	private ModelMapper mapper;

	@Test
	public void test001_createWorker() {
		ResponseEntity<HttpStatus> response = workerController.createWorker(new WorkerDto());
		assertEquals(CREATED, response.getBody());
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void test002_updateWorker() throws AppRuntimeException {
		ResponseEntity<HttpStatus> response = workerController.updateWorker("", new WorkerDto());
		assertEquals(OK, response.getBody());
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void test003_getWorker() throws AppRuntimeException {
		WorkerDto dto = new WorkerDto();
		dto.setName("test");
		when(mapper.map(any(), any())).thenReturn(dto);
		ResponseEntity<BaseWorkerDto> response = workerController.getWorker("id");
		assertNotNull(response.getBody());
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void test004_getAllWorkers() throws AppRuntimeException {
		Pageable pageableRequest = PageRequest.of(0, 2);

		WorkerDto worker1 = new WorkerDto();
		worker1.setName("integration test 1");
		worker1.setAge(33);
		worker1.setDescription("descrip1");
		worker1.setHeight(169.5d);
		worker1.setWeight(60.7d);
		worker1.setJob("Job1");

		WorkerDto worker2 = new WorkerDto();
		worker2.setName("integration test 2");
		worker2.setAge(60);
		worker2.setDescription("descrip2");
		worker2.setHeight(189.5d);
		worker2.setWeight(80.7d);
		worker2.setJob("Job2");

		List<Worker> workerList = newArrayList(new Worker(), new Worker());
		List<WorkerDto> workerDtoList = newArrayList(worker1, worker2);
		Page<Worker> pageWorker = new PageImpl<>(workerList, pageableRequest, 10);
		when(workerService.getWorkers(any(PageRequest.class))).thenReturn(pageWorker);

		when(mapper.map(pageWorker.getContent(), new TypeToken<List<WorkerDto>>() {}.getType()))
				.thenReturn(workerDtoList);

		ResponseEntity<RestResponsePage<WorkerDto>> response = workerController.getAllWorkers(0, 2);
		assertEquals(2, response.getBody().getContent().size());
		assertEquals(10, response.getBody().getTotalElements());
		assertEquals(0, response.getBody().getPageable().getPageNumber());
		assertEquals(2, response.getBody().getPageable().getPageSize());
		assertEquals(2, response.getBody().getContent().size());
	}
}