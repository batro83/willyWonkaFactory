package com.app.willywonkafactory.rest.controller;

import static org.slf4j.LoggerFactory.getLogger;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.willywonkafactory.rest.dto.WorkerDto;
import com.app.willywonkafactory.rest.service.impl.WorkerServiceImpl;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/worker")
public class WorkerController {

	private static final Logger LOG = getLogger(WorkerController.class);

	@Autowired
	private WorkerServiceImpl workerService;

	@ApiOperation(value = "Create new worker", notes = "Create new worker into collection")
	@PostMapping()
	public ResponseEntity<HttpStatus> createWorker(@RequestBody @Valid WorkerDto dto) {
		LOG.debug("WorkerController - createWorker");
		workerService.createWorker(dto);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

}
