package dev.batch.services;

import dev.batch.dto.QuizDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurriculumService {

	@Autowired
	RestTemplate restTemplate;


	// use service name in discovery service
	private static final String CURRICULUM_SERVICE_URL = "http://curriculum-service/curriculum";


	public QuizDTO getQuizById(Long id){
		String requestUrl = CURRICULUM_SERVICE_URL + "/quizzes/" + id;
		return restTemplate.getForObject(requestUrl, QuizDTO.class);
	}

	// similar method for topics

}
