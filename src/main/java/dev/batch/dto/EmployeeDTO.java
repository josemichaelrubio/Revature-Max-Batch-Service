package dev.batch.dto;

import java.util.List;
import java.util.Objects;

public class EmployeeDTO {

	private Employee employee;

	private List<EmployeeQuizScore> quizScores;

	private List<EmployeeTopicCompetency> topicCompetencies;

	private List<EmployeeQCFeedback> qcFeedbacks;

	public EmployeeDTO() {}

	public EmployeeDTO(Employee employee) {
		this.employee = employee;
	}

	public EmployeeDTO(Employee employee, List<EmployeeQuizScore> quizScores,
					   List<EmployeeTopicCompetency> topicCompetencies,
					   List<EmployeeQCFeedback> qcFeedbacks) {

		this.employee = employee;
		this.quizScores = quizScores;
		this.topicCompetencies = topicCompetencies;
		this.qcFeedbacks = qcFeedbacks;
	}

	public EmployeeDTO(Employee employee, List<EmployeeQuizScore> quizScores) {
		this.employee = employee;
		this.quizScores = quizScores;
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

	public List<EmployeeQCFeedback> getQcFeedbacks() {
		return qcFeedbacks;
	}

	public void setQcFeedbacks(List<EmployeeQCFeedback> qcFeedbacks) {
		this.qcFeedbacks = qcFeedbacks;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmployeeDTO that = (EmployeeDTO) o;
		return Objects.equals(employee, that.employee) && Objects.equals(quizScores, that.quizScores) && Objects.equals(topicCompetencies, that.topicCompetencies) && Objects.equals(qcFeedbacks, that.qcFeedbacks);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employee, quizScores, topicCompetencies, qcFeedbacks);
	}

	@Override
	public String toString() {
		return "EmployeeDTO{" +
				"employee=" + employee +
				", quizScores=" + quizScores +
				", topicCompetencies=" + topicCompetencies +
				", qcFeedbacks=" + qcFeedbacks +
				'}';
	}
}
