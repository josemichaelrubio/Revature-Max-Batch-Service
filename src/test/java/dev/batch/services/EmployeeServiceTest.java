package dev.batch.services;

import dev.batch.dto.Employee;
import dev.batch.dto.EmployeeDTO;
import dev.batch.models.Batch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


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

		EmployeeDTO[] empArray = new EmployeeDTO[4];
		empArray[0] = new EmployeeDTO(new Employee(1L, "1@web.com", "ASSOCIATE"));
		empArray[1] = new EmployeeDTO(new Employee(2L, "2@web.com", "ASSOCIATE"));
		empArray[2] = new EmployeeDTO(new Employee(3L, "3@web.com", "ASSOCIATE"));
		empArray[3] = new EmployeeDTO(new Employee(5L, "5@web.com", "ASSOCIATE"));
		List<EmployeeDTO> expected = new ArrayList<>(Arrays.asList(empArray));

		String url = EMPLOYEE_SERVICE_URL + "?id=1,2,3,5";
		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<EmployeeDTO[]>(new HttpHeaders()), EmployeeDTO[].class))
				.thenReturn(new ResponseEntity<>(empArray, HttpStatus.OK));

		List<EmployeeDTO> actual = employeeService.getEmployeesByListOfIds(idList, false, false, false);
		assertEquals(expected, actual);
	}

	@Test
	void getEmployeesByListOfIds_ReturnEverything() {
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(2L);
		idList.add(3L);
		idList.add(5L);

		EmployeeDTO[] empArray = new EmployeeDTO[4];
		empArray[0] = new EmployeeDTO(new Employee(1L, "1@web.com", "ASSOCIATE"));
		empArray[1] = new EmployeeDTO(new Employee(2L, "2@web.com", "ASSOCIATE"));
		empArray[2] = new EmployeeDTO(new Employee(3L, "3@web.com", "ASSOCIATE"));
		empArray[3] = new EmployeeDTO(new Employee(5L, "5@web.com", "ASSOCIATE"));
		List<EmployeeDTO> expected = new ArrayList<>(Arrays.asList(empArray));

		String url = EMPLOYEE_SERVICE_URL + "?id=1,2,3,5&field=quiz-scores,topic-competencies,qc-feedbacks";
		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<EmployeeDTO[]>(new HttpHeaders()), EmployeeDTO[].class))
				.thenReturn(new ResponseEntity<>(empArray, HttpStatus.OK));

		List<EmployeeDTO> actual = employeeService.getEmployeesByListOfIds(idList, true, true, true);
		assertEquals(expected, actual);
	}

	@Test
	void getOneValidEmployeeByEmail() {
		// Given
		List<String> emailList = new ArrayList<>();
		emailList.add("tucker@revature.net");

		Employee[] empArr = new Employee[1];
		empArr[0] = new Employee(1L, "tucker@revature.net", "ASSOCIATE");
		List<Employee> expectedEmployee = new ArrayList<>(Arrays.asList(empArr));


		// when
		String url = EMPLOYEE_SERVICE_URL + "?email=tucker@revature.net";
		when(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Employee[]>(new HttpHeaders()), Employee[].class))
				.thenReturn(new ResponseEntity<>(empArr, HttpStatus.OK));

		List<Employee> actual = employeeService.getEmployeesByListOfEmails(emailList);

		// then
		assertEquals(expectedEmployee, actual);
	}

	@Test
	void sendBatchEmailsTest() {
		BatchService batchService = mock(BatchService.class);
		//If someone has a better way to test a void method besides just testing it was actually ran. Please feel free to change this.
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(2L, "test@web.com", "ASSOCIATE"));
		employees.add(new Employee(3L, "test2@web.com", "ASSOCIATE"));
		employees.add(new Employee(4L, "test3@web.com", "ASSOCIATE"));

		List<String> employeeEmails = new ArrayList<>();
		employeeEmails.add("test@web.com");
		employeeEmails.add("test2@web.com");
		employeeEmails.add("test3@web.com");
		batchService.addAssociate(1L, employees);

		String name = "Java";
		String description = "A cool batch";
		String location = "Chicago";
		Long trainerId = 1L;

		String url = "http://employee-service/verify?" +
				"emails=" +
				employees.get(0).getEmail() + "," +
				employees.get(1).getEmail() + "," +
				employees.get(2).getEmail() +
				"&name=" +
				name +
				"&description=" +
				"A%20cool%20batch" +
				"&location=" +
				location +
				"&trainerId=" +
				trainerId;


		employeeService.sendBatchEmails(employeeEmails, name, description, location, trainerId);
		verify(restTemplate).exchange(url, HttpMethod.GET, new HttpEntity<String[]>(new HttpHeaders()), String[].class);

//		verify(employeeService, times(1)).sendBatchEmails(employeeEmails, "Java", "A cool batch", "Chicago", 1L);
	}
}
