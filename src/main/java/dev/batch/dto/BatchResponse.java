package dev.batch.dto;


import dev.batch.models.Batch;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BatchResponse {

    private Batch batch;
    private Map<Long, List<String>> quizAverage;
    private Map<Long, List<String>> competencyAverage;

    public BatchResponse() {}

    public BatchResponse(Batch batch, Map<Long, List<String>> quizAverage, Map<Long, List<String>> competencyAverage) {
        this.batch = batch;
        this.quizAverage = quizAverage;
        this.competencyAverage = competencyAverage;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchResponse that = (BatchResponse) o;
        return Objects.equals(batch, that.batch) && Objects.equals(quizAverage, that.quizAverage) && Objects.equals(competencyAverage, that.competencyAverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, quizAverage, competencyAverage);
    }

    @Override
    public String toString() {
        return "BatchResponse{" +
                "batch=" + batch +
                ", quizAverage=" + quizAverage +
                ", competencyAverage=" + competencyAverage +
                '}';
    }

    /* @Component
    private static class QuizAverage implements Serializable {
        private String quizName;
        private String average;
        private String count;

        public QuizAverage() {}

        public QuizAverage(String quizName, String average, String count) {
            this.quizName = quizName;
            this.average = average;
            this.count = count;
        }

        public String getQuizName() {
            return quizName;
        }

        public void setQuizName(String quizName) {
            this.quizName = quizName;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuizAverage that = (QuizAverage) o;
            return Objects.equals(quizName, that.quizName) && Objects.equals(average, that.average) && Objects.equals(count, that.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(quizName, average, count);
        }

        @Override
        public String toString() {
            return "QuizAverage{" +
                    "quizName='" + quizName + '\'' +
                    ", average='" + average + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    }

    @Component
    private static class CompetencyAverage implements Serializable {

        private String topicName;
        private String average;
        private String count;

        public CompetencyAverage() {}

        public CompetencyAverage(String topicName, String average, String count) {
            this.topicName = topicName;
            this.average = average;
            this.count = count;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompetencyAverage that = (CompetencyAverage) o;
            return Objects.equals(topicName, that.topicName) && Objects.equals(average, that.average) && Objects.equals(count, that.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(topicName, average, count);
        }

        @Override
        public String toString() {
            return "CompetencyAverage{" +
                    "topicName='" + topicName + '\'' +
                    ", average='" + average + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    } */

}
