package com.app.willywonkafactory.integration.unit.dto;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.runners.MethodSorters.NAME_ASCENDING;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.willywonkafactory.rest.dto.WorkerDto;

@RunWith(SpringRunner.class)
@FixMethodOrder(NAME_ASCENDING)
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