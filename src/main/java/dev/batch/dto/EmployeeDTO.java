package dev.batch.dto;

import java.util.List;
import java.util.Objects;

public class EmployeeDTO {

	private Employee employee;

	private List<EmployeeQuizScore> quizScores;

	private List<EmployeeTopicCompetency> topicCompetencies;

	public EmployeeDTO(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<EmployeeQuizScore> getQuizScores() {
		return quizScores;
	}

	public void setQuizScores(List<EmployeeQuizScore> quizScores) {
		this.quizScores = quizScores;
	}

	public List<EmployeeTopicCompetency> getTopicCompetencies() {
		return topicCompetencies;
	}

	public void setTopicCompetencies(List<EmployeeTopicCompetency> topicCompetencies) {
		this.topicCompetencies = topicCompetencies;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmployeeDTO that = (EmployeeDTO) o;
		return Objects.equals(employee, that.employee) && Objects.equals(quizScores, that.quizScores) && Objects.equals(topicCompetencies, that.topicCompetencies);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employee, quizScores, topicCompetencies);
	}

	@Override
	public String toString() {
		return "EmployeeDTO{" +
				"employee=" + employee +
				", quizScores=" + quizScores +
				", topicCompetencies=" + topicCompetencies +
				'}';
	}
}
