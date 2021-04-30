package dev.batch.dto;

import java.util.Objects;

public class EmployeeTopicCompetency {
	private Long employeeId;
	private Long topicId;
	// unsure on the type
	private float competency;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public float getCompetency() {
		return competency;
	}

	public void setCompetency(float competency) {
		this.competency = competency;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmployeeTopicCompetency that = (EmployeeTopicCompetency) o;
		return Float.compare(that.competency, competency) == 0 && Objects.equals(employeeId, that.employeeId) && Objects.equals(topicId, that.topicId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, topicId, competency);
	}

	@Override
	public String toString() {
		return "EmployeeTopicCompetency{" +
				"employeeId=" + employeeId +
				", topicId=" + topicId +
				", competency=" + competency +
				'}';
	}
}
