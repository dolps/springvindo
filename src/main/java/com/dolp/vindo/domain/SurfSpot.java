package com.dolp.vindo.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SurfSpot.
 */
@Entity
@Table(name = "surf_spot")
public class SurfSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SurfSpot name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SurfSpot surfSpot = (SurfSpot) o;
        if (surfSpot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), surfSpot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SurfSpot{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
