package com.app.willywonkafactory.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.controller.WorkerController;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.service.impl.WorkerServiceImpl;

@RunWith(SpringRunner.class)
public class WorkerControllerTest {

	@InjectMocks
	private WorkerController workerController;

	@Mock
	private WorkerServiceImpl workerService;

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
}