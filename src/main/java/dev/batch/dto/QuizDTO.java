package dev.batch.dto;

import java.util.Objects;

public class QuizDTO {
	private Long id;
	private String name;

	public QuizDTO() {
	}

	public QuizDTO(Long quizId, String name) {
		this.id = quizId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long quizId) {
		this.id = quizId;
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
		return Objects.equals(id, quizDTO.id) && Objects.equals(name, quizDTO.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public String toString() {
		return "QuizDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
