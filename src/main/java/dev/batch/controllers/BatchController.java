package dev.batch.controllers;
import dev.batch.dto.Employee;
import dev.batch.dto.EmployeeDTO;
import dev.batch.services.BatchService;
import dev.batch.dto.BatchResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        System.out.println("In batch service");
        return ResponseEntity.ok(batchService.getBatchInfoAndAveragesById(id));
    }


    @GetMapping("/{batch-id}/associates")
    public ResponseEntity<List<EmployeeDTO>> getAssociates(@PathVariable("batch-id") long batchId) {
        logger.info("Accessing all associates listed under batch id: "+batchId);
        return ResponseEntity.ok().body(batchService.getAllAssociates(batchId, false, false, false));
    }

    @PostMapping("/{batch-id}/associates")
    public ResponseEntity<List<Employee>> postAssociates(@PathVariable("batch-id") long batchId,
                                                         @RequestBody List<Employee> employeeEmails)
    {
        logger.info("trainer adding employees: "+employeeEmails+" to batch: "+batchId);
        return ResponseEntity.ok().body(batchService.addAssociate(batchId, employeeEmails));
    }

    @DeleteMapping("/{batch-id}/associates/{employee-id}")
    public ResponseEntity<HttpStatus> deleteAssociate(@PathVariable("batch-id") long batchId,
                                                      @PathVariable("employee-id") long employeeId)
    {
            logger.info("Trainer is removing employee, "+employeeId+", from batch: "+batchId);
            batchService.deleteAssociate(batchId, employeeId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }





}
