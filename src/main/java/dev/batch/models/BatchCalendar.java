package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="calendar")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchCalendar {

    @EmbeddedId
    private BatchCalendarId batchCalendarId;

    public BatchCalendarId getBatchCalendarId() {
        return batchCalendarId;
    }

    public void setBatchCalendarId(BatchCalendarId batchCalendarId) {
        this.batchCalendarId = batchCalendarId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchCalendar that = (BatchCalendar) o;
        return Objects.equals(batchCalendarId, that.batchCalendarId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchCalendarId);
    }

    public class BatchCalendarId implements Serializable {
        private Long day;
        @ManyToOne
        private Batch batch;

        public Long getDay() {
            return day;
        }

        public void setDay(Long day) {
            this.day = day;
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
            BatchCalendarId that = (BatchCalendarId) o;
            return Objects.equals(day, that.day) && Objects.equals(batch, that.batch);
        }

        @Override
        public int hashCode() {
            return Objects.hash(day, batch);
        }
    }
}
