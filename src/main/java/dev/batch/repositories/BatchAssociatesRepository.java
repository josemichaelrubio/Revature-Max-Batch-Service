package dev.batch.repositories;

import dev.batch.models.BatchAssociates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchAssociatesRepository extends JpaRepository<BatchAssociates, BatchAssociates.BatchAssociatesId> {

    @Query("select b from BatchAssociates b where b.batchAssociatesId.batch.id = :batchId")
    public List<BatchAssociates> findAllInBatchById(long batchId);

    @Query("delete from BatchAssociates b where b.batchAssociatesId.employeeId = :employeeId")
    public void deleteByEmployeeId(Long employeeId);
}
