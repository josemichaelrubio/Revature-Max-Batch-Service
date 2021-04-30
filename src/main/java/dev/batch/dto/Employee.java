package dev.batch.dto;

import java.util.Objects;
import java.util.Set;

public class Employee {
	private Long id;
	private String email;
	// string or mirror the enum
	private String role;
	// Can easily be changed depending on how the JSON is returned to us.
	// It'll probably be easier to match our objects/dtos to that then to wrestle jackson to map it differently
	private EmployeeInformation employeeInformation;

	private Set<EmployeeQuizScores> quizScores;
	private Set<EmployeeTopicCompetency> topicCompetencies;

	// Since we don't use the other tables / aspects we don't need to map them.
	// Should they become important later, we can update dtos to match

	public Employee() {
	}

	public Employee(Long id, String email, String role) {
		this.id = id;
		this.email = email;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public EmployeeInformation getEmployeeInformation() {
		return employeeInformation;
	}

	public void setEmployeeInformation(EmployeeInformation employeeInformation) {
		this.employeeInformation = employeeInformation;
	}

	public Set<EmployeeQuizScores> getQuizScores() {
		return quizScores;
	}

	public void setQuizScores(Set<EmployeeQuizScores> quizScores) {
		this.quizScores = quizScores;
	}

	public Set<EmployeeTopicCompetency> getTopicCompetencies() {
		return topicCompetencies;
	}

	public void setTopicCompetencies(Set<EmployeeTopicCompetency> topicCompetencies) {
		this.topicCompetencies = topicCompetencies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) && Objects.equals(email, employee.email) && Objects.equals(role, employee.role) && Objects.equals(employeeInformation, employee.employeeInformation) && Objects.equals(quizScores, employee.quizScores) && Objects.equals(topicCompetencies, employee.topicCompetencies);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, role, employeeInformation, quizScores, topicCompetencies);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", employeeInformation=" + employeeInformation +
				", quizScores=" + quizScores +
				", topicCompetencies=" + topicCompetencies +
				'}';
	}
}
