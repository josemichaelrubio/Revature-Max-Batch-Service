package dev.batch.services;

import dev.batch.dto.Employee;
import dev.batch.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
public class EmployeeService {

	@Autowired
	RestTemplate restTemplate;

	// use service name in discovery service
	private static final String EMPLOYEE_SERVICE_URL = "http://employee-service/employees";

	public Employee getEmployeeById(long id){
		// url for /employees/{id}
		String requestUrl = EMPLOYEE_SERVICE_URL + "/" + id;
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> request = new HttpEntity<>(headers);
		ResponseEntity<Employee> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, request, Employee.class);
		return responseEntity.getBody();
		// or if we don't need any headers
//		String requestUrl = EMPLOYEE_SERVICE_URL + id;
//		return restTemplate.getForObject(requestUrl, Employee.class);
	}

	public List<EmployeeDTO> getEmployeesByListOfIds(List<Long> employeeIds, boolean includeQuizScores, boolean includeTopicCompetencies){
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(EMPLOYEE_SERVICE_URL);
		StringBuilder stringOfIds = new StringBuilder();
		employeeIds.forEach(id-> stringOfIds.append(",").append(id));
		stringOfIds.deleteCharAt(0); // probably a better way to do this

		// Don't remember the name of the query param
		uriComponentsBuilder.queryParam("id", stringOfIds);
		String uri = uriComponentsBuilder.toUriString();
		uri += "&field=";
		if (includeQuizScores)
			uri += "quiz-scores,";
		if (includeTopicCompetencies)
			uri += "topic-competencies";


		System.out.println(uri);
		ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<EmployeeDTO[]>(new HttpHeaders()), EmployeeDTO[].class);
		System.out.println(responseEntity.getBody().length);
		System.out.println(responseEntity.getBody());
		if (responseEntity.getBody() != null){
			return Arrays.asList(responseEntity.getBody());
		}
		return new ArrayList<>();
	}
}
