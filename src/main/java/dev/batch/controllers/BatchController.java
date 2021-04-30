package dev.batch.controllers;
import dev.batch.BatchService;
import dev.batch.dtos.BatchResponse;
import dev.batch.models.Batch;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/batches")
public class BatchController {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    BatchService batchService;


    Logger logger = LogManager.getLogger(BatchController.class);


    @ResponseBody
    @GetMapping(value = "/{batch-id}", produces = "application/json")
    public ResponseEntity<BatchResponse> handleGetBatchInfoById(@PathVariable("batch-id") long id) {
        return ResponseEntity.ok(batchService.getBatchInfoAndAveragesById(id));
    }


    @GetMapping("/{batch-id}/associates")
    public ResponseEntity<List<Employee>> getAssociates(@PathVariable("batch-id") long batchId) {
        logger.info("Accessing all associates listed under batch id: "+batchId);
        return ResponseEntity.ok().body(batchService.getAllAssociates(batchId));
    }

    @PostMapping("/{batch-id}/associates")
    public ResponseEntity<List<Employee>> postAssociates(@PathVariable("batch-id") long batchId,
                                                         @RequestHeader("Authorization")String authorization,
                                                         @RequestBody List<Employee> employees)
    {
        logger.info("trainer adding employees: "+employees+" to batch: "+batchId);
        return ResponseEntity.ok().body(batchService.addAssociate(batchId, employees));

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{batch-id}/associates/{employee-id}")
    public ResponseEntity<HttpStatus> deleteAssociate(@PathVariable("batch-id") long batchId,
                                                      @RequestHeader("Authorization") String authorization,
                                                      @PathVariable("employee-id") long employeeId)
    {
            logger.info("Trainer is removing employee, "+employeeId+", from batch: "+batchId);
            batchService.deleteAssociate(batchId, employeeId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }





}
