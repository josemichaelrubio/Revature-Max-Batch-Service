package dev.batch.services;

import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchAssociatesService {

	@Autowired
	BatchAssociatesRepository batchAssociatesRepository;

	public ResponseEntity<Long> getBatchIdByEmployeeId(long employeeId) {
		BatchAssociates batchAssociates = batchAssociatesRepository.getBatchByEmployeeId(employeeId);
		Long batchId = batchAssociates.getBatchAssociatesId().getBatch().getId();
		return new ResponseEntity<>(batchId, HttpStatus.OK);
	}
}
