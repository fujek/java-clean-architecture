package io.github.mat3e.project.query;

import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "projects")
public class SimpleProjectQuery {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;

    @PersistenceConstructor
    SimpleProjectQuery() {
    }

    public SimpleProjectQuery(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return id;
    }

    String getName() {
        return name;
    }
}
