package dev.batch.dto;

import java.util.Objects;

public class Tech {

    private long id;
    String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tech tech = (Tech) o;
        return id == tech.id && Objects.equals(name, tech.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Tech{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
