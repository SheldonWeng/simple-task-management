package com.sheldonweng.taskmanage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sheldonweng.taskmanage.utils.DateUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Task execute record entity
 */
@Entity
@Table(name = "task_execute_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskExecuteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "taskExecuteRecords", allowSetters = true)
    private Task task;

    public TaskExecuteRecord() {
        this.startTime = DateUtils.getCurrentZonedDateTime();
    }

    public TaskExecuteRecord(Task task, Long userId) {
        this.userId = userId;
        this.startTime = DateUtils.getCurrentZonedDateTime();
        this.task = task;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public TaskExecuteRecord userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public TaskExecuteRecord startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public TaskExecuteRecord endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Task getTask() {
        return task;
    }

    public TaskExecuteRecord task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskExecuteRecord)) {
            return false;
        }
        return id != null && id.equals(((TaskExecuteRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskExecuteRecord{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
