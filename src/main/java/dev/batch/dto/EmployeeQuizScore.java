package dev.batch.dto;

import java.util.Objects;

public class EmployeeQuizScore {
	private EmployeeQuizScoreId id;
	private float score;

	public EmployeeQuizScore(EmployeeQuizScoreId id, float score) {
		this.id = id;
		this.score = score;

	}

	public EmployeeQuizScoreId getId() {
		return id;
	}

	public void setId(EmployeeQuizScoreId id) {
		this.id = id;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmployeeQuizScore that = (EmployeeQuizScore) o;
		return Float.compare(that.score, score) == 0 && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score);
	}

	@Override
	public String toString() {
		return "EmployeeQuizScore{" +
				"id=" + id +
				", score=" + score +
				'}';
	}

	public static class EmployeeQuizScoreId {
		private Long employeeId;
		private Long quizId;

		public EmployeeQuizScoreId(Long employeeId, Long quizId) {
			this.employeeId = employeeId;
			this.quizId = quizId;
		}

		public Long getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(Long employeeId) {
			this.employeeId = employeeId;
		}

		public Long getQuizId() {
			return quizId;
		}

		public void setQuizId(Long quizId) {
			this.quizId = quizId;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			EmployeeQuizScoreId that = (EmployeeQuizScoreId) o;
			return Objects.equals(employeeId, that.employeeId) && Objects.equals(quizId, that.quizId);
		}

		@Override
		public int hashCode() {
			return Objects.hash(employeeId, quizId);
		}

		@Override
		public String toString() {
			return "EmployeeQuizScoreId{" +
					"employeeId=" + employeeId +
					", quizId=" + quizId +
					'}';
		}
	}
}
