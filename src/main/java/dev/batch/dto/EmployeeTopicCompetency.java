package dev.batch.dto;

import java.util.Objects;

public class EmployeeTopicCompetency {
	private EmployeeTopicCompetencyId id;
	// unsure on the type
	private float competency;

	// favNotes would go here if we need it

	public EmployeeTopicCompetencyId getId() {
		return id;
	}

	public void setId(EmployeeTopicCompetencyId id) {
		this.id = id;
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
		return Float.compare(that.competency, competency) == 0 && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, competency);
	}

	@Override
	public String toString() {
		return "EmployeeTopicCompetency{" +
				"id=" + id +
				", competency=" + competency +
				'}';
	}

	public static class EmployeeTopicCompetencyId {
		private Long employeeId;
		private Long topicId;

		public EmployeeTopicCompetencyId(Long employeeId, Long topicId) {
			this.employeeId = employeeId;
			this.topicId = topicId;
		}

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

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			EmployeeTopicCompetencyId that = (EmployeeTopicCompetencyId) o;
			return Objects.equals(employeeId, that.employeeId) && Objects.equals(topicId, that.topicId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(employeeId, topicId);
		}

		@Override
		public String toString() {
			return "EmployeeTopicCompetencyId{" +
					"employeeId=" + employeeId +
					", topicId=" + topicId +
					'}';
		}
	}
}
