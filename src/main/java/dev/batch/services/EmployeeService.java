package dev.batch.services;

import dev.batch.dto.Employee;
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

	public List<Employee> getEmployeesByListOfIds(List<Long> employeeIds){
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(EMPLOYEE_SERVICE_URL);
		StringBuilder stringOfIds = new StringBuilder();
		employeeIds.forEach(id-> stringOfIds.append(",").append(id));
		stringOfIds.deleteCharAt(0); // probably a better way to do this

		// Don't remember the name of the query param
		uriComponentsBuilder.queryParam("id", stringOfIds);
		String uri = uriComponentsBuilder.toUriString();
		ResponseEntity<Employee[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<Employee[]>(new HttpHeaders()), Employee[].class);
		if (responseEntity.getBody() != null){
			return Arrays.asList(responseEntity.getBody());
		}
		return new ArrayList<>();
	}
}
