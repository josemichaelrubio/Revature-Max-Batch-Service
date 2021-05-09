package dev.batch.services;

import dev.batch.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {

    @InjectMocks
    private BatchService batchService;
    @MockBean
    private CurriculumService curriculumService;



    @Test
    public void shouldGetQCFeedbackForEmployees() {

        // given
        Employee employee1 = new Employee(1L, "e@email.com", "ASSOCIATE");
        Employee employee2 = new Employee(2L, "t@email.com", "ASSOCIATE");

        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(1L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(1L, 2L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(2L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(2L, 2L);

        EmployeeQCFeedback employee1QCFeedback1 = new EmployeeQCFeedback(employee1QCFeedback1Id, 4, 5);
        EmployeeQCFeedback employee1QCFeedback2 = new EmployeeQCFeedback(employee1QCFeedback2Id, 2, 2);
        EmployeeQCFeedback employee2QCFeedback1 = new EmployeeQCFeedback(employee2QCFeedback1Id, 3, 4);
        EmployeeQCFeedback employee2QCFeedback2 = new EmployeeQCFeedback(employee2QCFeedback2Id, 1,2);

        List<EmployeeQCFeedback> employee1QCFeedback = new LinkedList<>();
        List<EmployeeQCFeedback> employee2QCFeedback = new LinkedList<>();
        employee1QCFeedback.add(employee1QCFeedback1);
        employee1QCFeedback.add(employee1QCFeedback2);
        employee2QCFeedback.add(employee2QCFeedback1);
        employee2QCFeedback.add(employee2QCFeedback2);

        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1, employee1QCFeedback);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2, employee2QCFeedback);

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeDTOS.add(employeeDTO1);
        employeeDTOS.add(employeeDTO2);

        // when
        List<EmployeeQCFeedback> expected = new LinkedList<>();
        expected.add(employee1QCFeedback1);
        expected.add(employee1QCFeedback2);
        expected.add(employee2QCFeedback1);
        expected.add(employee2QCFeedback2);

        System.out.println(expected);
        System.out.println(employeeDTOS);

        List<EmployeeQCFeedback> actual = batchService.getQCFeedBackFromEmployees(employeeDTOS);

        System.out.println(actual);

        // then
        assertEquals(expected, actual);


    }

    @Test
    public void shouldGrabQCNames() {

        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(1L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(1L, 2L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(2L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(2L, 2L);

        EmployeeQCFeedback employee1QCFeedback1 = new EmployeeQCFeedback(employee1QCFeedback1Id, 4, 5);
        EmployeeQCFeedback employee1QCFeedback2 = new EmployeeQCFeedback(employee1QCFeedback2Id, 2, 2);
        EmployeeQCFeedback employee2QCFeedback1 = new EmployeeQCFeedback(employee2QCFeedback1Id, 3, 4);
        EmployeeQCFeedback employee2QCFeedback2 = new EmployeeQCFeedback(employee2QCFeedback2Id, 1,2);

        List<EmployeeQCFeedback> qcFeedbacks = new LinkedList<>();
        qcFeedbacks.add(employee1QCFeedback1);
        qcFeedbacks.add(employee1QCFeedback2);
        qcFeedbacks.add(employee2QCFeedback1);
        qcFeedbacks.add(employee2QCFeedback2);

        List<Long> qcIds = new ArrayList<>();
        qcIds.add(1L);
        qcIds.add(2L);
        List<QCDTO> qcdtos = new ArrayList<>();
        qcdtos.add(new QCDTO(1L, "QC 1"));
        qcdtos.add(new QCDTO(2L, "QC 2"));


        when(curriculumService.getQCNamesByListOfIds(qcIds)).thenReturn(qcdtos);

        List<QCDTO> actual = batchService.grabQCNames(qcFeedbacks);

        assertEquals(qcdtos, actual);

    }

    @Test
    public void shouldComputeQCRatingAverages() {
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(1L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(1L, 2L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(2L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(2L, 2L);

        EmployeeQCFeedback employee1QCFeedback1 = new EmployeeQCFeedback(employee1QCFeedback1Id, 4, 5);
        EmployeeQCFeedback employee1QCFeedback2 = new EmployeeQCFeedback(employee1QCFeedback2Id, 2, 2);
        EmployeeQCFeedback employee2QCFeedback1 = new EmployeeQCFeedback(employee2QCFeedback1Id, 3, 4);
        EmployeeQCFeedback employee2QCFeedback2 = new EmployeeQCFeedback(employee2QCFeedback2Id, 1,2);

        List<EmployeeQCFeedback> qcFeedbacks = new LinkedList<>();
        qcFeedbacks.add(employee1QCFeedback1);
        qcFeedbacks.add(employee1QCFeedback2);
        qcFeedbacks.add(employee2QCFeedback1);
        qcFeedbacks.add(employee2QCFeedback2);

        List<QCDTO> qcdtos = new ArrayList<>();
        qcdtos.add(new QCDTO(1L, "QC 1"));
        qcdtos.add(new QCDTO(2L, "QC 2"));

        when(batchService.grabQCNames(qcFeedbacks)).thenReturn(qcdtos);

        List<String> qc1 = new ArrayList<>();
        qc1.add("QC 1");
        qc1.add("3.5");
        qc1.add("2");
        List<String> qc2 = new ArrayList<>();
        qc2.add("QC 2");
        qc2.add("1.5");
        qc2.add("2");

        Map<Long, List<String>> expected = new TreeMap<>();
        expected.put(1L, qc1);
        expected.put(2L, qc2);

        Map<Long, List<String>> actual = batchService.getQCRatingsAverages(qcFeedbacks);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldComputeQCInstructorFeedbackAverages() {
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(1L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee1QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(1L, 2L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback1Id = new EmployeeQCFeedback.QCFeedbackId(2L, 1L);
        EmployeeQCFeedback.QCFeedbackId employee2QCFeedback2Id = new EmployeeQCFeedback.QCFeedbackId(2L, 2L);

        EmployeeQCFeedback employee1QCFeedback1 = new EmployeeQCFeedback(employee1QCFeedback1Id, 4, 5);
        EmployeeQCFeedback employee1QCFeedback2 = new EmployeeQCFeedback(employee1QCFeedback2Id, 2, 2);
        EmployeeQCFeedback employee2QCFeedback1 = new EmployeeQCFeedback(employee2QCFeedback1Id, 3, 4);
        EmployeeQCFeedback employee2QCFeedback2 = new EmployeeQCFeedback(employee2QCFeedback2Id, 1,2);

        List<EmployeeQCFeedback> qcFeedbacks = new LinkedList<>();
        qcFeedbacks.add(employee1QCFeedback1);
        qcFeedbacks.add(employee1QCFeedback2);
        qcFeedbacks.add(employee2QCFeedback1);
        qcFeedbacks.add(employee2QCFeedback2);

        List<QCDTO> qcdtos = new ArrayList<>();
        qcdtos.add(new QCDTO(1L, "QC 1"));
        qcdtos.add(new QCDTO(2L, "QC 2"));

        when(batchService.grabQCNames(qcFeedbacks)).thenReturn(qcdtos);

        List<String> qc1 = new ArrayList<>();
        qc1.add("QC 1");
        qc1.add("4.5");
        qc1.add("2");
        List<String> qc2 = new ArrayList<>();
        qc2.add("QC 2");
        qc2.add("2.0");
        qc2.add("2");

        Map<Long, List<String>> expected = new TreeMap<>();
        expected.put(1L, qc1);
        expected.put(2L, qc2);

        Map<Long, List<String>> actual = batchService.getQCInstructorFeedbackAverages(qcFeedbacks);

        assertEquals(expected, actual);
    }


}
