package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="batch_associates")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchAssociates {

    @EmbeddedId
    private BatchAssociatesId batchAssociatesId;

    public BatchAssociates() {
    }

    public BatchAssociatesId getBatchAssociatesId() {
        return batchAssociatesId;
    }

    public void setBatchAssociatesId(BatchAssociatesId batchAssociatesId) {
        this.batchAssociatesId = batchAssociatesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchAssociates that = (BatchAssociates) o;
        return Objects.equals(batchAssociatesId, that.batchAssociatesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchAssociatesId);
    }

    @Override
    public String toString() {
        return "BatchAssociates{" +
                "batchAssociatesId=" + batchAssociatesId +
                '}';
    }

    @Embeddable
    public class BatchAssociatesId implements Serializable {
        private Long employeeId;
        @ManyToOne
        private Batch batch;

        public Long getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Long employeeId) {
            this.employeeId = employeeId;
        }

        public Batch getBatch() {
            return batch;
        }

        public void setBatch(Batch batch) {
            this.batch = batch;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BatchAssociatesId that = (BatchAssociatesId) o;
            return Objects.equals(employeeId, that.employeeId) &&
                    Objects.equals(batch, that.batch);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeId, batch);
        }

        @Override
        public String toString() {
            return "BatchAssociatesId{" +
                    "employeeId=" + employeeId +
                    ", batch=" + batch +
                    '}';
        }
    }
}
