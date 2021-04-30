package dev.batch.dto;

import java.util.Objects;

public class EmployeeQuizScores {
	private Long employeeId;
	private Long quizId;
	private float score;

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
		EmployeeQuizScores that = (EmployeeQuizScores) o;
		return Float.compare(that.score, score) == 0 && Objects.equals(employeeId, that.employeeId) && Objects.equals(quizId, that.quizId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, quizId, score);
	}

	@Override
	public String toString() {
		return "EmployeeQuizScores{" +
				"employeeId=" + employeeId +
				", quizId=" + quizId +
				", score=" + score +
				'}';
	}
}
