package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="associates")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchAssociates {

    @EmbeddedId
    private BatchAssociatesId batchAssociatesId;

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
    }
}
