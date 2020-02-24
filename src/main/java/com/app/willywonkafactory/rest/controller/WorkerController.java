package com.app.willywonkafactory.rest.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.willywonkafactory.rest.dto.BaseWorkerDto;
import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.exception.AppRuntimeException;
import com.app.willywonkafactory.rest.model.Worker;
import com.app.willywonkafactory.rest.pagination.RestResponsePage;
import com.app.willywonkafactory.rest.service.WorkerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/worker")
public class WorkerController {

	private static final Logger LOG = getLogger(WorkerController.class);

	@Autowired
	private WorkerService workerService;

	@Autowired
	private ModelMapper mapper;

	@ApiOperation(value = "Create new worker", notes = "Create new worker")
	@PostMapping
	public ResponseEntity<HttpStatus> createWorker(@RequestBody @Valid WorkerDto dto) {
		LOG.debug("WorkerController - createWorker");
		workerService.createWorker(dto);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update worker fields", notes = "Update worker fields")
	@PatchMapping("/{id}")
	public ResponseEntity<HttpStatus> updateWorker(@PathVariable("id") String idWorker, @RequestBody WorkerDto dto)
			throws AppRuntimeException {
		LOG.debug("WorkerController - update Worker {id}", idWorker);
		workerService.editWorker(idWorker, dto);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@ApiOperation(value = "Get worker information", notes = "Get worker information by id")
	@GetMapping("/{id}")
	public ResponseEntity<BaseWorkerDto> getWorker(@PathVariable("id") String idWorker) throws AppRuntimeException {
		LOG.debug("WorkerController - getWorker {id}", idWorker);
		BaseWorkerDto map = mapper.map(workerService.getWorker(idWorker), BaseWorkerDto.class);
		return ResponseEntity.ok(map);
	}

	@ApiOperation(value = "Get all workers", notes = "Get all workers paginated")
	@GetMapping("/all")
	public ResponseEntity<RestResponsePage<WorkerDto>> getAllWorkers(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "5") int size) throws AppRuntimeException {
		LOG.debug("WorkerController - getAllWorkers");
		Pageable pageableRequest = PageRequest.of(page, size);

		Page<Worker> workerPage = workerService.getWorkers(pageableRequest);
		List<WorkerDto> s = mapper.map(workerPage.getContent(), new TypeToken<List<WorkerDto>>() {}.getType());
		return ResponseEntity
				.ok(new RestResponsePage<WorkerDto>(s, workerPage.getPageable(), workerPage.getTotalElements()));
	}
}