package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="batch_associates")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchAssociates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    public BatchAssociates() {
    }
    public BatchAssociates(Long employeeId, Batch batchid) {
        this.employeeId =employeeId;
        this.batch =batchid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        BatchAssociates that = (BatchAssociates) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(batch, that.batch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, batch);
    }

    @Override
    public String toString() {
        return "BatchAssociates{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", batch=" + batch +
                '}';
    }
}
