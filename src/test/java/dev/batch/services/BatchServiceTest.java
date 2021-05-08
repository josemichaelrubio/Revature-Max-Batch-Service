package dev.batch.services;

import dev.batch.dto.Employee;
import dev.batch.dto.EmployeeDTO;
import dev.batch.dto.EmployeeQuizScore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BatchServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @InjectMocks
    private CurriculumService curriculumService;

    @Autowired
    private BatchService batchService;



    @Test
    public void shouldGetQuizScoresForEmployees() {

        // given
        Employee employee1 = new Employee(1L, "e@email.com", "ASSOCIATE");
        Employee employee2 = new Employee(2L, "t@email.com", "ASSOCIATE");

        EmployeeQuizScore.EmployeeQuizScoreId employee1QuizScore1Id = new EmployeeQuizScore.EmployeeQuizScoreId(1L, 1L);
        EmployeeQuizScore.EmployeeQuizScoreId employee1QuizScore2Id = new EmployeeQuizScore.EmployeeQuizScoreId(1L, 2L);
        EmployeeQuizScore.EmployeeQuizScoreId employee2QuizScore1Id = new EmployeeQuizScore.EmployeeQuizScoreId(2L, 1L);
        EmployeeQuizScore.EmployeeQuizScoreId employee2QuizScore2Id = new EmployeeQuizScore.EmployeeQuizScoreId(2L, 2L);

        EmployeeQuizScore employee1QuizScore1 = new EmployeeQuizScore(employee1QuizScore1Id, 95);
        EmployeeQuizScore employee1QuizScore2 = new EmployeeQuizScore(employee1QuizScore2Id, 80);
        EmployeeQuizScore employee2QuizScore1 = new EmployeeQuizScore(employee2QuizScore1Id, 90);
        EmployeeQuizScore employee2QuizScore2 = new EmployeeQuizScore(employee2QuizScore2Id, 98);

        List<EmployeeQuizScore> employee1QuizScores = new LinkedList<>();
        List<EmployeeQuizScore> employee2QuizScores = new LinkedList<>();
        employee1QuizScores.add(employee1QuizScore1);
        employee1QuizScores.add(employee1QuizScore2);
        employee2QuizScores.add(employee2QuizScore1);
        employee2QuizScores.add(employee2QuizScore2);

        EmployeeDTO employeeDTO1 = new EmployeeDTO(employee1, employee1QuizScores);
        EmployeeDTO employeeDTO2 = new EmployeeDTO(employee2, employee2QuizScores);

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        employeeDTOS.add(employeeDTO1);
        employeeDTOS.add(employeeDTO2);

        // when
        List<EmployeeQuizScore> expected = new LinkedList<>();
        expected.add(employee1QuizScore1);
        expected.add(employee1QuizScore2);
        expected.add(employee2QuizScore1);
        expected.add(employee2QuizScore2);

        System.out.println(expected);
        System.out.println(employeeDTOS);

        List<EmployeeQuizScore> actual = batchService.getQuizScoresFromEmployees(employeeDTOS);

        System.out.println(actual);

        // then
        assertEquals(expected, actual);


    }


}
