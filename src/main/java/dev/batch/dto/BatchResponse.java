package dev.batch.dto;


import dev.batch.models.Batch;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BatchResponse {

    private Batch batch;
    private Map<Long, List<String>> quizAverage;
    private Map<Long, List<String>> competencyAverage;
    private Map<Long, List<String>> qcRatingsAverage;
    private Map<Long, List<String>> qcInstructorFeedbackAverage;

    public BatchResponse() {}

    public BatchResponse(Batch batch, Map<Long, List<String>> quizAverage, Map<Long,
            List<String>> competencyAverage, Map<Long, List<String>> qcRatingsAverage,
                         Map<Long, List<String>> qcInstructorFeedbackAverage) {
        this.batch = batch;
        this.quizAverage = quizAverage;
        this.competencyAverage = competencyAverage;
        this.qcRatingsAverage = qcRatingsAverage;
        this.qcInstructorFeedbackAverage = qcInstructorFeedbackAverage;

    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Map<Long, List<String>> getQuizAverage() {
        return quizAverage;
    }

    public void setQuizAverage(Map<Long, List<String>> quizAverage) {
        this.quizAverage = quizAverage;
    }

    public Map<Long, List<String>> getCompetencyAverage() {
        return competencyAverage;
    }

    public void setCompetencyAverage(Map<Long, List<String>> competencyAverage) {
        this.competencyAverage = competencyAverage;
    }

    public Map<Long, List<String>> getQcRatingsAverage() {
        return qcRatingsAverage;
    }

    public void setQcRatingsAverage(Map<Long, List<String>> qcRatingsAverage) {
        this.qcRatingsAverage = qcRatingsAverage;
    }

    public Map<Long, List<String>> getQcInstructorFeedbackAverage() {
        return qcInstructorFeedbackAverage;
    }

    public void setQcInstructorFeedbackAverage(Map<Long, List<String>> qcInstructorFeedbackAverage) {
        this.qcInstructorFeedbackAverage = qcInstructorFeedbackAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchResponse that = (BatchResponse) o;
        return Objects.equals(batch, that.batch) && Objects.equals(quizAverage, that.quizAverage) && Objects.equals(competencyAverage, that.competencyAverage) && Objects.equals(qcRatingsAverage, that.qcRatingsAverage) && Objects.equals(qcInstructorFeedbackAverage, that.qcInstructorFeedbackAverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, quizAverage, competencyAverage, qcRatingsAverage, qcInstructorFeedbackAverage);
    }

    @Override
    public String toString() {
        return "BatchResponse{" +
                "batch=" + batch +
                ", quizAverage=" + quizAverage +
                ", competencyAverage=" + competencyAverage +
                ", qcRatingsAverage=" + qcRatingsAverage +
                ", qcInstructorFeedbackAverage=" + qcInstructorFeedbackAverage +
                '}';
    }

}
