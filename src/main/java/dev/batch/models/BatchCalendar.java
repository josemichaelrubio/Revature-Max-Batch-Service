package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
<<<<<<< HEAD
@Table(name = "batch_calendar")
=======
@Table(name="calendar")
>>>>>>> 58ea2b10aac4966fd08c5dbcda67b998583d92ce
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class BatchCalendar {

    @EmbeddedId
    private BatchCalendarId batchCalendarId;

<<<<<<< HEAD

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
=======
    public BatchCalendarId getBatchCalendarId() {
        return batchCalendarId;
    }

    public void setBatchCalendarId(BatchCalendarId batchCalendarId) {
        this.batchCalendarId = batchCalendarId;
    }

>>>>>>> 58ea2b10aac4966fd08c5dbcda67b998583d92ce
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchCalendar that = (BatchCalendar) o;
        return Objects.equals(batchCalendarId, that.batchCalendarId);
    }
<<<<<<< HEAD
=======

>>>>>>> 58ea2b10aac4966fd08c5dbcda67b998583d92ce
    @Override
    public int hashCode() {
        return Objects.hash(batchCalendarId);
    }
<<<<<<< HEAD
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
=======

    public class BatchCalendarId implements Serializable {
        private Long day;
        @ManyToOne
        private Batch batch;

        public Long getDay() {
            return day;
        }

        public void setDay(Long day) {
            this.day = day;
>>>>>>> 58ea2b10aac4966fd08c5dbcda67b998583d92ce
        }

        public Batch getBatch() {
            return batch;
        }

        public void setBatch(Batch batch) {
            this.batch = batch;
        }

<<<<<<< HEAD

    }

}


=======
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
>>>>>>> 58ea2b10aac4966fd08c5dbcda67b998583d92ce
