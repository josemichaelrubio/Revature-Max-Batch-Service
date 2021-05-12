package dev.batch.repositories;

import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.services.BatchAssociatesService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BatchAssociatesRepository extends JpaRepository<BatchAssociates, BatchAssociates.BatchAssociatesId> {

    @Query("select b from BatchAssociates b where b.batchAssociatesId.batch.id = :batchId")
    public List<BatchAssociates> findAllInBatchById(long batchId);

    @Transactional
    @Modifying
    @Query("delete from BatchAssociates b where b.batchAssociatesId.employeeId = :employeeId")
    public void deleteByEmployeeId(Long employeeId);

    @Query("select b from BatchAssociates b where b.batchAssociatesId.employeeId = :employeeId")
    BatchAssociates getBatchByEmployeeId(long employeeId);
}
