package dev.batch.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CurriculumServiceTest {

    @InjectMocks
    CurriculumService curriculumService;
    @MockBean
    RestTemplate restTemplate;

    @Test
    public void getQCNamesFromListOfIds() {

    }


}
