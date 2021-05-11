package dev.batch.services;

import dev.batch.dto.QCDTO;
import dev.batch.dto.QuizDTO;
import dev.batch.dto.TopicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CurriculumService {

	@Autowired
	RestTemplate restTemplate;


	// use service name in discovery service
//	private static final String CURRICULUM_SERVICE_URL = "http://40.122.154.60/curriculum";
	private static final String CURRICULUM_SERVICE_URL = "http://curriculum-service/curriculum";


	public QuizDTO getQuizById(Long id){
		String requestUrl = CURRICULUM_SERVICE_URL + "/quizzes/" + id;
		return restTemplate.getForObject(requestUrl, QuizDTO.class);
	}

	public List<QuizDTO> getQuizzesByListOfIds(List<Long> quizIds) {
		String requestUrl = CURRICULUM_SERVICE_URL + "/quizzes";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl);
		StringBuilder stringOfIds = new StringBuilder();
		quizIds.forEach(id-> stringOfIds.append(",").append(id));
		stringOfIds.deleteCharAt(0);

		uriComponentsBuilder.queryParam("ids", stringOfIds);
		String uri = uriComponentsBuilder.toUriString();

		ResponseEntity<QuizDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<QuizDTO[]>(new HttpHeaders()), QuizDTO[].class);
		if (responseEntity.getBody() != null){
			return Arrays.asList(responseEntity.getBody());
		}
		return new ArrayList<>();


	}


	// similar method for topics

	public List<TopicDTO> getTopicsByListOfIds(List<Long> topicIds) {
		String requestUrl = CURRICULUM_SERVICE_URL + "/topics";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl);
		StringBuilder stringOfIds = new StringBuilder();
		topicIds.forEach(id -> stringOfIds.append(",").append(id));
		stringOfIds.deleteCharAt(0);

		uriComponentsBuilder.queryParam("topicIds", stringOfIds);
		String uri = uriComponentsBuilder.toUriString();

		ResponseEntity<TopicDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<TopicDTO[]>(new HttpHeaders()), TopicDTO[].class);
		if (responseEntity.getBody() != null){
			return Arrays.asList(responseEntity.getBody());
		}
		return new ArrayList<>();

	}

	public List<QCDTO> getQCNamesByListOfIds(List<Long> qcIds) {
		String requestUrl = CURRICULUM_SERVICE_URL + "/qcs";
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl);
		StringBuilder stringOfIds = new StringBuilder();
		qcIds.forEach(id -> stringOfIds.append(",").append(id));
		stringOfIds.deleteCharAt(0);

		uriComponentsBuilder.queryParam("qcIds", stringOfIds);
		String uri = uriComponentsBuilder.toUriString();

		ResponseEntity<QCDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<QCDTO[]>(new HttpHeaders()), QCDTO[].class);
		if (responseEntity.getBody() != null) {
			return Arrays.asList(responseEntity.getBody());
		}

		return new ArrayList<>();
	}

}
