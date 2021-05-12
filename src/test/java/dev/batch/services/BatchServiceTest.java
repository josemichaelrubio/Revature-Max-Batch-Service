package dev.batch.services;

import dev.batch.dto.*;
import dev.batch.models.Batch;
import dev.batch.models.BatchAssociates;
import dev.batch.repositories.BatchAssociatesRepository;
import dev.batch.repositories.BatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {

    @InjectMocks
    private BatchService batchService;
    @MockBean
    private CurriculumService curriculumService;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private BatchAssociatesRepository batchAssociatesRepository;
    @MockBean
    private BatchRepository batchRepository;


    @Test
    void getBatchInfoAndAveragesById(){
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeDTOS.add(new EmployeeDTO(
                new Employee(1L, "test1@web.com", "ASSOCIATE"),
                Arrays.asList(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(1L, 1L), 75.8f),
                        new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(1L, 2L), 93.2f)),
                Arrays.asList(new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(1L, 1L), 3.2f),
                        new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(1L, 2L), 4.2f)),
                Arrays.asList(new EmployeeQCFeedback(new EmployeeQCFeedback.QCFeedbackId(1L, 1L), 3, 4),
                        new EmployeeQCFeedback(new EmployeeQCFeedback.QCFeedbackId(1L, 2L), 2, 3))
        ));
        employeeDTOS.add(new EmployeeDTO(
                new Employee(2L, "test2@web.com", "ASSOCIATE"),
                Arrays.asList(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 1L), 84.3f),
                        new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 2L), 78.1f)),
                Arrays.asList(new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(2L, 1L), 4.1f),
                        new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(2L, 2L), 1.4f)),
                Arrays.asList(new EmployeeQCFeedback(new EmployeeQCFeedback.QCFeedbackId(2L, 1L), 4, 5),
                        new EmployeeQCFeedback(new EmployeeQCFeedback.QCFeedbackId(2L, 2L), 3, 4))
        ));

        // getAllAssociates
        when(batchAssociatesRepository.findAllInBatchById(1L)).thenReturn(Arrays.asList(
                new BatchAssociates(new BatchAssociates.BatchAssociatesId(1L, new Batch(1L))),
                new BatchAssociates(new BatchAssociates.BatchAssociatesId(2L, new Batch(1L)))
        ));
        when(employeeService.getEmployeesByListOfIds(Arrays.asList(1L, 2L), true, true, true))
                .thenReturn(employeeDTOS);

        // getBasicBatchInfo
        Batch expectedBatch = new Batch(1L, "batchName", "batch description", 3L, "batch Location", 1L);
        when(batchRepository.findById(1L)).thenReturn(Optional.of(expectedBatch));

        // getQuizAveragesInfo -> grabQuizNames
        QuizDTO quizDTO1 = new QuizDTO(1L, "quiz 1");
        QuizDTO quizDTO2 = new QuizDTO(2L, "quiz 2");
        when(curriculumService.getQuizzesByListOfIds(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(quizDTO1, quizDTO2));

        // getTopicCompetencyAveragesInfo -> grabTopicsAndTechNames
        TopicDTO topicDTO1 = new TopicDTO(1L, "tech 1");
        TopicDTO topicDTO2 = new TopicDTO(2L, "tech 2");
        when(curriculumService.getTopicsByListOfIds(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(topicDTO1, topicDTO2));

        // getQCRatingsAverages -> grabQCNames
        // And for getQCInstructorFeedbackAverages?
        QCDTO qcdto1 = new QCDTO(1L, "QC 1");
        QCDTO qcdto2 = new QCDTO(2L, "QC 2");
        when(curriculumService.getQCNamesByListOfIds(Arrays.asList(1L, 2L)))
                .thenReturn(Arrays.asList(qcdto1, qcdto2));

        Map<Long, List<String>> expectedQuizAverage = new TreeMap<>();
        expectedQuizAverage.put(1L, Arrays.asList(quizDTO1.getName(), String.format("%.2f", (75.8f + 84.3f)/2f), "2"));
        expectedQuizAverage.put(2L, Arrays.asList(quizDTO2.getName(), String.format("%.2f", (93.2f + 78.1f)/2f), "2"));

        Map<Long, List<String>> expectedCompetenctyAverage = new TreeMap<>();
        expectedCompetenctyAverage.put(1L, Arrays.asList(topicDTO1.getTechName(), String.format("%.1f", (3.2f + 4.1f)/2f), "2"));
        expectedCompetenctyAverage.put(2L, Arrays.asList(topicDTO2.getTechName(), String.format("%.1f", (4.2f + 1.4f)/2f), "2"));

        Map<Long, List<String>> expectedQcRatingsAverage = new TreeMap<>();
        expectedQcRatingsAverage.put(1L, Arrays.asList(qcdto1.getName(), String.format("%.1f", 3.5f), "2"));
        expectedQcRatingsAverage.put(2L, Arrays.asList(qcdto2.getName(), String.format("%.1f", 2.5f), "2"));

        Map<Long, List<String>> expectedQcInstructorFeedbackAverage = new TreeMap<>();
        expectedQcInstructorFeedbackAverage.put(1L, Arrays.asList(qcdto1.getName(), String.format("%.1f", 4.5f), "2"));
        expectedQcInstructorFeedbackAverage.put(2L, Arrays.asList(qcdto2.getName(), String.format("%.1f", 3.5f), "2"));

        BatchResponse expected = new BatchResponse(expectedBatch, expectedQuizAverage, expectedCompetenctyAverage, expectedQcRatingsAverage, expectedQcInstructorFeedbackAverage);
        BatchResponse actual = batchService.getBatchInfoAndAveragesById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getQuizScoresFromEmployees() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeDTOS.add(new EmployeeDTO(new Employee(1L, "test1@web.com", "ASSOCIATE")));
        EmployeeQuizScore[] e1q = {new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(1L, 1L), 85.4f)};
        employeeDTOS.get(0).setQuizScores(Arrays.asList(e1q));
        EmployeeQuizScore[] e2q = {
                new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 1L), 85.4f),
                new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 2L), 85.4f)
        };
        employeeDTOS.add(new EmployeeDTO(new Employee(2L, "test2@web.com", "ASSOCIATE")));
        employeeDTOS.get(1).setQuizScores(Arrays.asList(e2q));

        List<EmployeeQuizScore> expected = new ArrayList<>(Arrays.asList(e1q));
        expected.addAll(Arrays.asList(e2q));

        List<EmployeeQuizScore> actual = batchService.getQuizScoresFromEmployees(employeeDTOS);
        assertEquals(expected, actual);
    }

    @Test
    void getTopicCompetenciesFromEmployees() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeDTOS.add(new EmployeeDTO(new Employee(1L, "test1@web.com", "ASSOCIATE")));
        EmployeeTopicCompetency[] e1tc = {new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(1L, 1L), 3)};
        employeeDTOS.get(0).setTopicCompetencies(Arrays.asList(e1tc));
        EmployeeTopicCompetency[] e2tc = {
                new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(2L, 1L), 2.5f),
                new EmployeeTopicCompetency(new EmployeeTopicCompetency.EmployeeTopicCompetencyId(2L, 2L), 4.3f)
        };
        employeeDTOS.add(new EmployeeDTO(new Employee(2L, "test2@web.com", "ASSOCIATE")));
        employeeDTOS.get(1).setTopicCompetencies(Arrays.asList(e2tc));

        List<EmployeeTopicCompetency> expected = new ArrayList<>(Arrays.asList(e1tc));
        expected.addAll(Arrays.asList(e2tc));

        List<EmployeeTopicCompetency> actual = batchService.getTopicCompetenciesFromEmployees(employeeDTOS);
        assertEquals(expected, actual);
    }

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
    void getQuizAveragesInfo() {
        List<EmployeeQuizScore> quizScores = new ArrayList<>();
        quizScores.add(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(1L, 1L), 75.8f));
        quizScores.add(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 1L), 83f));
        quizScores.add(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(3L, 1L), 95.4f));
        quizScores.add(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(1L, 2L), 93.2f));
        quizScores.add(new EmployeeQuizScore(new EmployeeQuizScore.EmployeeQuizScoreId(2L, 2L), 81f));

        Long[] idList = {1L, 2L};

        List<QuizDTO> quizReturnList = new ArrayList<>(Arrays.asList(new QuizDTO(1L, "quiz 1"), new QuizDTO(2L, "quiz 2")));
        when(curriculumService.getQuizzesByListOfIds(Arrays.asList(idList))).thenReturn(quizReturnList);

        String q1name = "quiz 1";
        String q1average = String.format("%.2f", (75.8f + 83f + 95.4f)/3f);
        String q1count = "3";
        String q2name = "quiz 2";
        String q2average = String.format("%.2f", (93.2f + 81f)/2f);
        String q2count = "2";
        Map<Long, List<String>> expected = new TreeMap<>();
        expected.put(1L, Arrays.asList(q1name, q1average, q1count));
        expected.put(2L, Arrays.asList(q2name, q2average, q2count));
//        Map<Long, List<String>> actual = batchService.getQuizAveragesInfo(quizScores);
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

    @Test
    void addAssociate() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "test1@web.com", "ASSOCIATE"));
        employees.add(new Employee(2L, "test2@web.com", "GUEST"));
        employees.add(new Employee(3L, "test3@web.com", "ASSOCIATE"));
        employees.add(new Employee(5L, "test5@web.com", "GUEST"));

        List<String> employeeEmails = new ArrayList<>();
        employees.forEach(employee -> employeeEmails.add(employee.getEmail()));

        Batch batch = new Batch(1L, "batchName", "batch description", 3L, "batch Location", 1L);
        when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
        when(employeeService.getEmployeesByListOfEmails(Arrays.asList("test1@web.com", "test2@web.com", "test3@web.com", "test5@web.com")))
                .thenReturn(employees);

        List<Employee> expectedReturn = Arrays.asList(employees.get(1), employees.get(3));
        List<Employee> actual = batchService.addAssociate(1L, employees);

        verify(batchAssociatesRepository).save(new BatchAssociates(new BatchAssociates.BatchAssociatesId(1L, batch)));
        verify(batchAssociatesRepository).save(new BatchAssociates(new BatchAssociates.BatchAssociatesId(3L, batch)));
        verify(employeeService).sendBatchEmails(employeeEmails, batch.getName(), batch.getDescription(), batch.getLocation(), batch.getTrainerId());

        assertEquals(expectedReturn, actual);
    }


}
