package com.app.willywonkafactory.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dao.WorkerDao;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(NAME_ASCENDING)
@ActiveProfiles("test")
public class WorkerControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WorkerDao workerDao;

	@Test
	public void test000_cleanDb() throws Exception {
		workerDao.deleteAll();
	}

	@Test
	public void test001_createWorker_nullFields_badRequest() throws Exception {
		final String path = "/worker";
		HttpEntity<WorkerDto> request = new HttpEntity<>(new WorkerDto());
		final ResponseEntity<String> response = restTemplate.exchange(path, POST, request, String.class);
		assertEquals(BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void test002_createWorker_ok() throws Exception {
		final String path = "/worker";

		WorkerDto dto = new WorkerDto();
		dto.setName("integration test");
		dto.setAge(33);
		dto.setDescription("descrip");
		dto.setHeight(189.5d);
		dto.setWeight(80.7d);
		dto.setJob("Job1");

		HttpEntity<WorkerDto> request = new HttpEntity<>(dto);
		final ResponseEntity<String> response = restTemplate.exchange(path, POST, request, String.class);
		assertEquals(OK, response.getStatusCode());

		Worker worker = workerDao.findByName("integration test");
		assertEquals(dto.getName(), worker.getName());
		assertEquals(dto.getAge(), worker.getAge(), 0);
		assertEquals(dto.getDescription(), worker.getDescription());
		assertEquals(dto.getHeight(), worker.getHeight(), 0);
		assertEquals(dto.getWeight(), worker.getWeight(), 0);
		assertEquals(dto.getJob(), worker.getJob());
	}

	@Test
	public void test003_editWorker_NotExist() throws Exception {
		final String path = "/worker/{id}";
		final String id = "testIdNoExist";
		final ResponseEntity<String> response = restTemplate.exchange(path, PATCH, new HttpEntity<>(null), String.class,
				id);
		assertEquals(BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void test004_editWorker_ok() throws Exception {
		final String path = "/worker/{id}";
		final String id = "testIdExist";
		final ResponseEntity<String> response = restTemplate.exchange(path, PATCH, new HttpEntity<>(null), String.class,
				id);
		assertEquals(BAD_REQUEST, response.getStatusCode());
	}

	@Test(expected = AppRuntimeException.class)
	public void test005_getWorker_ko() throws Exception {
		final String path = "/worker/{id}";
		final String id = "testIdNoExist";
		final ResponseEntity<String> response = restTemplate.exchange(path, GET, new HttpEntity<>(null), String.class,
				id);
	}

	@Test
	public void test006_getWorker_ok() throws Exception {

	}

	@Test
	public void test007_getAllWorkers_ok() throws Exception {

	}
}