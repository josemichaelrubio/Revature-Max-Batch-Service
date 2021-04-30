package dev.batch.repositories;

import dev.batch.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

//    BatchSummary getById(long id);

 //      @Query("SELECT b FROM Batch b LEFT JOIN FETCH b.associates LEFT JOIN FETCH b.instructor WHERE b.id = :batchId")
  //     Batch getBatchById(@Param("batchId") long id);
//
//    @Query("SELECT b FROM Batch b WHERE :employee MEMBER OF b.associates")
//    Batch getBatchForAssociate(@Param("employee") Employee employee);
//
//    @Query("SELECT b FROM Batch b WHERE b.instructor = :employee")
//    List<Batch> getBatchesForInstructor(Employee employee);
//
//    @Query(value = "select * from employee_batch where employee_id = :emp", nativeQuery = true)
//    long findBatchIdByEmployeeId(@Param("emp")long empId);

}
