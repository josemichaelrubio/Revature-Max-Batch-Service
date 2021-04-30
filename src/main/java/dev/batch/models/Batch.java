package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="batch")
@Component
@JsonIgnoreProperties("hibernateLazyInitializer")
@Scope("prototype")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long trainerId;
    private String location;
    private Long curriculumId;


    public Batch() {}

    public Batch(long id) {
        this.id = id;
    }

    public Batch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Batch(String name, String description, Long trainerId, String location, Long curriculumId) {
        this.name = name;
        this.description = description;
        this.trainerId = trainerId;
        this.location = location;
        this.curriculumId = curriculumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getCurriculumId() {
        return curriculumId;
    }

    public void setCurriculumId(Long curriculumId) {
        this.curriculumId = curriculumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch = (Batch) o;
        return Objects.equals(id, batch.id) && Objects.equals(name, batch.name) && Objects.equals(description, batch.description) && Objects.equals(trainerId, batch.trainerId) && Objects.equals(location, batch.location) && Objects.equals(curriculumId, batch.curriculumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, trainerId, location, curriculumId);
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trainerId=" + trainerId +
                ", location='" + location + '\'' +
                ", curriculumId=" + curriculumId +
                '}';
    }
}
