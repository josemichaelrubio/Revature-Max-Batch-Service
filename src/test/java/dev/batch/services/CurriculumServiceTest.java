package dev.batch.services;

import dev.batch.dto.EmployeeDTO;
import dev.batch.dto.QCDTO;
import dev.batch.dto.QuizDTO;
import dev.batch.dto.TopicDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

    @InjectMocks
    CurriculumService curriculumService;

    @MockBean
    RestTemplate restTemplate;

    private static final String CURRICULUM_SERVICE_URL = "http://curriculum-service/curriculum";

    @Test
    public void testGetQCNamesFromListOfIds() {

        // Build URI for endpoint call
        String requestUrl = CURRICULUM_SERVICE_URL + "/qcs";
        String uri = requestUrl + "?qcIds=1,2";

        // Expected list of QC ids
        List<Long> qcIds = new ArrayList<>();
        qcIds.add(1L);
        qcIds.add(2L);

        // Use QCDTO Array to keep method call happy
        QCDTO[] qcdtoArr = new QCDTO[2];
        qcdtoArr[0] = new QCDTO(1L, "test1");
        qcdtoArr[1] = new QCDTO(2l, "test2");

        // Expected List of QCDTOs to be returned
        List<QCDTO> expected = new ArrayList<>(Arrays.asList(qcdtoArr));

        // Stub the curriculum microservice call with mock data
        when(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<QCDTO[]>(new HttpHeaders()), QCDTO[].class))
                .thenReturn(new ResponseEntity(qcdtoArr, HttpStatus.OK));

        // Test the curriculum microservice call
        List<QCDTO> actual = curriculumService.getQCNamesByListOfIds(qcIds);

        // Assert that the correct data is returned
        assertEquals(expected, actual);
    }


    @Test
    public void testGetQuizzesFromListOfIds() {

        // Build URI for endpoint call
        String requestUrl = CURRICULUM_SERVICE_URL + "/quizzes";
        String uri = requestUrl + "?ids=1,2";

        // Expected list of Quiz ids
        List<Long> quizIds = new ArrayList<>();
        quizIds.add(1L);
        quizIds.add(2L);

        // Use QuizDTO Array to keep method call happy
        QuizDTO[] quizDtoArr = new QuizDTO[2];
        quizDtoArr[0] = new QuizDTO(1L, "quiz1");
        quizDtoArr[1] = new QuizDTO(2L, "quiz2");

        // Expected List of QuizDtos
        List<QuizDTO> expected = new ArrayList<>(Arrays.asList(quizDtoArr));

        // Stub the call to the curriculum microservice with mock data
        when(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<QuizDTO[]>(new HttpHeaders()), QuizDTO[].class))
                .thenReturn(new ResponseEntity(quizDtoArr, HttpStatus.OK));

        // Test the curriculum microservice call
        List<QuizDTO> actual = curriculumService.getQuizzesByListOfIds(quizIds);

        // Assert that the correct data is returned
        assertEquals(expected, actual);

    }

    @Test
    public void testGetTopicsFromListOfIds() {

        // Build the URI for endpoint call
        String requestUrl = CURRICULUM_SERVICE_URL + "/topics";
        String uri = requestUrl + "?topicIds=1,2";

        // Expected list of Topic ids
        List<Long> topicIds = new ArrayList<>();
        topicIds.add(1L);
        topicIds.add(2L);

        // Use TopicDTO Array to keep method call happy
        TopicDTO[] topicDtoArr = new TopicDTO[2];
        topicDtoArr[0] = new TopicDTO(1L, "topic1");
        topicDtoArr[1] = new TopicDTO(2L, "topic2");

        // Expected List of TopicDtos
        List<TopicDTO> expected = new ArrayList<>(Arrays.asList(topicDtoArr));

        // Stub the curriculum microservice call using mock data
        when(restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<TopicDTO[]>(new HttpHeaders()), TopicDTO[].class))
                .thenReturn(new ResponseEntity(topicDtoArr, HttpStatus.OK));

        // Test the curriculum microservice call
        List<TopicDTO> actual = curriculumService.getTopicsByListOfIds(topicIds);

        // Assert that the correct data is returned
        assertEquals(expected, actual);

    }
}
