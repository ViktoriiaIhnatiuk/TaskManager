package com.example.taskmanager.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "priorities")
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PriorityName priorityName;

    public Priority() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriorityName getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(PriorityName priorityName) {
        this.priorityName = priorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Priority priority = (Priority) o;
        return Objects.equals(id, priority.id) && priorityName == priority.priorityName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priorityName);
    }

    @Override
    public String toString() {
        return "Priority{"
                + "id=" + id
                + ", priorityName=" + priorityName
                + '}';
    }

    public enum PriorityName {
        LOW,
        MEDIUM,
        HIGH;
    }
}
