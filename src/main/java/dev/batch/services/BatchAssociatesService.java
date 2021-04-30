package dev.batch.services;

import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchAssociatesService {

	@Autowired
	BatchAssociatesRepository batchAssociatesRepository;

}
