package com.example.taskmanager.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "statuses")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value =EnumType.STRING)
    @Column(unique = true)
    private StatusName statusName;

    public Status() {
    }

    public Status(StatusName statusName) {
    this.statusName = statusName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", statusName=" + statusName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(id, status.id) && statusName == status.statusName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusName);
    }

    public enum StatusName {
        TO_DO,
        IN_PROGRESS,
        DONE;

    }
}
