package dev.batch.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

//    @OneToOne
//    @JoinColumn(name = "instructor_id")
//    private Employee instructor;

//    @ManyToMany
//    @JoinTable(name = "employee_batch",
//            joinColumns = @JoinColumn(name = "batch_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
//
//

//    @OneToMany
//    private List<BatchAssociates> associates = new ArrayList<>();


    public Batch() {}

    public Batch(long id) {
        this.id = id;
    }

    public Batch(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Batch(String name, String description, Long trainerId, String location
//            , List<BatchAssociates> associates
    ) {
        this.name = name;
        this.description = description;
        this.trainerId = trainerId;
        this.location = location;
//        this.associates = associates;
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
//    public Employee getInstructor() {
//        return instructor;
//    }
//
//    public void setInstructor(Employee instructor) {
//        this.instructor = instructor;
//    }
//
//    public List<Employee> getAssociates() {
//        return associates;
//    }
//
//    public void setAssociates(List<Employee> associates) {
//        this.associates = associates;
//    }
//
//    public void addAssociate(Employee associate) {
//        this.associates.add(associate);
//    }
//
//    public void removeAssociate(Employee associate) {
//        this.associates.remove(associate);
//    }


//    public List<BatchAssociates> getAssociates() {
//        return associates;
//    }
//
//    public void setAssociates(List<BatchAssociates> associates) {
//        this.associates = associates;
//    }
//    public List<BatchAssociates> setAssociate(List<BatchAssociates> associates, Long employeeId) {
//        associates.add(employeeId);
//        return associates;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch = (Batch) o;
        return Objects.equals(id, batch.id) &&
                Objects.equals(name, batch.name) &&
                Objects.equals(description, batch.description) &&
                Objects.equals(trainerId, batch.trainerId) &&
                Objects.equals(location, batch.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, trainerId, location);
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", trainerId=" + trainerId +
                ", location='" + location + '\'' +
                '}';
    }
}
