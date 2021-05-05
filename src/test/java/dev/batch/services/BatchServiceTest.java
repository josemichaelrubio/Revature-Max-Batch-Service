package dev.batch.services;

import dev.batch.repositories.BatchAssociatesRepository;
import dev.batch.repositories.BatchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {

    @Mock
    private BatchAssociatesRepository batchAssociatesRepository;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private CurriculumService curriculumService;
    @Mock
    private BatchRepository batchRepository;
    private BatchService underTest;


    @BeforeEach
    void setUp() {
        underTest = new BatchService(batchRepository, batchAssociatesRepository, employeeService, curriculumService);
    }



    @Test
    @Disabled
    public void GetListOfAssociatesBasicInfo() {

    }


}
