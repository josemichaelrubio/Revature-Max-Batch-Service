package dev.batch.dto;

import java.util.Objects;

public class QuizDTO {
	private Long quizId;
	private String name;

	public QuizDTO() {
	}

	public QuizDTO(Long quizId, String name) {
		this.quizId = quizId;
		this.name = name;
	}

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		QuizDTO quizDTO = (QuizDTO) o;
		return Objects.equals(quizId, quizDTO.quizId) && Objects.equals(name, quizDTO.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(quizId, name);
	}

	@Override
	public String toString() {
		return "QuizDTO{" +
				"quizId=" + quizId +
				", name='" + name + '\'' +
				'}';
	}
}
