package dev.batch.dto;

import java.util.Objects;

public class EmployeeQCFeedback {

    private QCFeedbackId id;
    private Integer associateRating;
    private Integer instructorFeedback;

    public EmployeeQCFeedback() {}

    public EmployeeQCFeedback(QCFeedbackId id, Integer associateRating, Integer instructorFeedback) {
        this.id = id;
        this.associateRating = associateRating;
        this.instructorFeedback = instructorFeedback;
    }

    public QCFeedbackId getId() {
        return id;
    }

    public void setId(QCFeedbackId id) {
        this.id = id;
    }

    public Integer getAssociateRating() {
        return associateRating;
    }

    public void setAssociateRating(Integer associateRating) {
        this.associateRating = associateRating;
    }

    public Integer getInstructorFeedback() {
        return instructorFeedback;
    }

    public void setInstructorFeedback(Integer instructorFeedback) {
        this.instructorFeedback = instructorFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeQCFeedback that = (EmployeeQCFeedback) o;
        return Objects.equals(id, that.id) && Objects.equals(associateRating, that.associateRating) && Objects.equals(instructorFeedback, that.instructorFeedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, associateRating, instructorFeedback);
    }

    @Override
    public String toString() {
        return "QCFeedback{" +
                "id=" + id +
                ", associateRating=" + associateRating +
                ", instructorFeedback=" + instructorFeedback +
                '}';
    }

    public static class QCFeedbackId {

        private Long employeeId;
        private Long qcId;

        public QCFeedbackId() {}

        public QCFeedbackId(Long employeeId, Long qcId) {
            this.employeeId = employeeId;
            this.qcId = qcId;
        }

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public Long getQcId() {
            return qcId;
        }

        public void setQcId(Long qcId) {
            this.qcId = qcId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QCFeedbackId that = (QCFeedbackId) o;
            return Objects.equals(employeeId, that.employeeId) && Objects.equals(qcId, that.qcId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeId, qcId);
        }

        @Override
        public String toString() {
            return "QCFeedbackId{" +
                    "employeeId=" + employeeId +
                    ", qcId=" + qcId +
                    '}';
        }
    }
}
