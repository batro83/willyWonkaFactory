package com.app.willywonkafactory.unit.dto;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dto.WorkerDto;

@RunWith(SpringRunner.class)
public class WorkerDtoTest {

	private static Validator validator;

	@BeforeClass
	public static void setupValidatorInstance() {
		validator = buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void test001_createWorker_NotNullFields() {
		WorkerDto dto = new WorkerDto();
		Set<ConstraintViolation<WorkerDto>> violations = validator.validate(dto);
		assertEquals(5, violations.size());
	}
}