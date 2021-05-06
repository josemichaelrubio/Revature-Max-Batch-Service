package dev.batch.dto;

import java.util.List;
import java.util.Objects;

public class QCDTO {

    Long id;
    String name;
    List<Tech> techs;

    public QCDTO() { }

    public QCDTO(Long id, String name, List<Tech> techs) {
        this.id = id;
        this.name = name;
        this.techs = techs;
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

    public List<Tech> getTechs() {
        return techs;
    }

    public void setTechs(List<Tech> techs) {
        this.techs = techs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QCDTO qcdto = (QCDTO) o;
        return Objects.equals(id, qcdto.id) && Objects.equals(name, qcdto.name) && Objects.equals(techs, qcdto.techs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, techs);
    }

    @Override
    public String toString() {
        return "QCDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", techs=" + techs +
                '}';
    }
}
