package com.app.willywonkafactory.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dao.WorkerDao;
import com.app.willywonkafactory.rest.dto.BaseWorkerDto;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.pagination.RestResponsePage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@FixMethodOrder(NAME_ASCENDING)
@ActiveProfiles("test")
public class WorkerControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private WorkerDao workerDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void test000_cleanDb() throws Exception {
		workerDao.deleteAll();
	}

	@Test
	public void test001_getWorker_ko() throws Exception {
		final String path = "/worker/{id}";
		final String id = "testIdNoExist";
		final ResponseEntity<BaseWorkerDto> response = restTemplate.exchange(path, GET, EMPTY, BaseWorkerDto.class, id);
		assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void test002_getWorker_ok() throws Exception {
		final Worker worker = objectMapper.readValue(new URL("file:src/test/resources/fixtures/worker/worker1.json"),
				Worker.class);
		workerDao.save(worker);

		final String path = "/worker/{id}";
		final String id = "5e53910fcffcf26519e12444";
		final ResponseEntity<BaseWorkerDto> response = restTemplate.exchange(path, GET, EMPTY, BaseWorkerDto.class, id);
		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(worker.getName(), response.getBody().getName());
		assertEquals(worker.getJob(), response.getBody().getJob());
		assertEquals(worker.getAge(), response.getBody().getAge(), 0);
	}

	@Test
	public void test003_getAllWorkers_ok() throws Exception {
		final List<Worker> worker = objectMapper.readValue(
				new URL("file:src/test/resources/fixtures/worker/worker2.json"), new TypeReference<List<Worker>>() {});
		workerDao.saveAll(worker);

		ParameterizedTypeReference<RestResponsePage<WorkerDto>> responseType = new ParameterizedTypeReference<RestResponsePage<WorkerDto>>() {};

		final String path = "/worker/all?page=0&size=3";
		final ResponseEntity<RestResponsePage<WorkerDto>> response = restTemplate.exchange(path, GET, EMPTY,
				responseType);
		assertEquals(OK, response.getStatusCode());
	}

	@Test
	public void test004_createWorker_nullFields_badRequest() throws Exception {
		final String path = "/worker";
		HttpEntity<WorkerDto> request = new HttpEntity<>(new WorkerDto());
		final ResponseEntity<String> response = restTemplate.exchange(path, POST, request, String.class);
		assertEquals(BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void test005_createWorker_ok() throws Exception {
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
	public void test006_editWorker_NotExist() throws Exception {
		final String path = "/worker/{id}?_method=patch";
		final String id = "testIdNoExist";
		HttpEntity<WorkerDto> entity = new HttpEntity<>(new WorkerDto());

		final ResponseEntity<String> response = restTemplate.exchange(path, PATCH, entity, String.class, id);
		assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void test007_editWorker_ok() throws Exception {
		final String path = "/worker/{id}";
		final String id = "5e53910fcffcf26519e12444";

		WorkerDto dto = new WorkerDto();
		dto.setName("update name");

		HttpEntity<WorkerDto> request = new HttpEntity<>(dto);
		final ResponseEntity<String> response = restTemplate.exchange(path, PATCH, request, String.class, id);
		assertEquals(OK, response.getStatusCode());

		Optional<Worker> worker = workerDao.findById(id);
		assertEquals(dto.getName(), worker.get().getName());
	}
}