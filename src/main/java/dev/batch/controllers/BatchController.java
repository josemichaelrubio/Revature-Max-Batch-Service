package dev.batch.controllers;
import dev.batch.models.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/batches")
public class BatchController {


/*    GET /batches/{batch-id}
    GET /batches/{batch-id}/associates
    POST /batches/{batch-id}/associates
    DELETE /batches/{batch-id}/associates/{employee-id}    */

    @Autowired
    RestTemplate restTemplate;

    @GetMapping()
    public String hi(){
        return "hello";
    }

}
