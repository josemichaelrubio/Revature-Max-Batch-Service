package dev.batch.repositories;

import dev.batch.models.BatchAssociates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchAssociatesRepository extends JpaRepository<BatchAssociates, BatchAssociates.BatchAssociatesId> {
    List<BatchAssociates> findAllInBatch(long batchId);
    public void deleteByEmployeeId(Long employeeId);
}
