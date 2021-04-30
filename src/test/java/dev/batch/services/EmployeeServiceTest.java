package dev.batch.services;

import dev.batch.dto.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;
	@MockBean
	private RestTemplate restTemplate;
	private static final String EMPLOYEE_SERVICE_URL = "http://employee-service/employees";

	@Test
	void getEmployeeByIdValidReturn() {
		String url = EMPLOYEE_SERVICE_URL + "/9";
		Employee expected = new Employee(9L, "user9@web.com", "role");
		// Or restTemplate.getForObject() if using that
		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), Employee.class))
				.thenReturn(new ResponseEntity<>(expected, HttpStatus.OK));
		Employee actual = employeeService.getEmployeeById(9L);
		// uses .equals of employee dto (which uses .equals of nested classes if present)
		assertEquals(expected, actual);
	}

	@Test
	void getEmployeesByListOfIdsValid() {
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(2L);
		idList.add(3L);
		idList.add(5L);

		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee(1L, "1@web.com", "role"));
		employeeList.add(new Employee(2L, "2@web.com", "role"));
		employeeList.add(new Employee(3L, "3@web.com", "role"));
		employeeList.add(new Employee(5L, "5@web.com", "role"));

		String url = EMPLOYEE_SERVICE_URL + "?id=1,2,3,5";
		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Employee[]>(new HttpHeaders()), Employee[].class))
				.thenReturn(new ResponseEntity<>((Employee[]) employeeList.toArray(), HttpStatus.OK));

		List<Employee> actual = employeeService.getEmployeesByListOfIds(idList);
		assertEquals(employeeList, actual);
	}
}
