package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "batch_calendar")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchCalendar {

    @EmbeddedId
    private BatchCalendarId batchCalendarId;

    public BatchCalendar() {
    }

    public BatchCalendar(BatchCalendarId batchCalendarId) {
        this.batchCalendarId = batchCalendarId;

    }

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

    @Override
    public String toString() {
        return "BatchCalendar{" +
                "batchCalendarId=" + batchCalendarId +
                '}';
    }


    @Embeddable
   public class BatchCalendarId implements Serializable {

        private Long dayId;


        @ManyToOne(fetch = FetchType.LAZY)
        private Batch batch;

        public BatchCalendarId() {
        }

        public Long getDayId() {
            return dayId;
        }

        public void setDayId(Long dayId) {
            this.dayId = dayId;
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
            return Objects.equals(dayId, that.dayId) && Objects.equals(batch, that.batch);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dayId, batch);
        }

        @Override
        public String toString() {
            return "BatchCalendarId{" +
                    "dayId=" + dayId +
                    ", batch=" + batch +
                    '}';
        }
    }
}
