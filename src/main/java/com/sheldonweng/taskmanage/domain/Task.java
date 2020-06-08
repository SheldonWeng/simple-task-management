package com.sheldonweng.taskmanage.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.sheldonweng.taskmanage.domain.enumeration.TaskStatus;

/**
 * Task entity
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "task_name", length = 30, nullable = false)
    private String taskName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    @Column(name = "plan_start_time")
    private ZonedDateTime planStartTime;

    @Column(name = "plan_end_time")
    private ZonedDateTime planEndTime;

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

    public Task userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Task taskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Task createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getPlanStartTime() {
        return planStartTime;
    }

    public Task planStartTime(ZonedDateTime planStartTime) {
        this.planStartTime = planStartTime;
        return this;
    }

    public void setPlanStartTime(ZonedDateTime planStartTime) {
        this.planStartTime = planStartTime;
    }

    public ZonedDateTime getPlanEndTime() {
        return planEndTime;
    }

    public Task planEndTime(ZonedDateTime planEndTime) {
        this.planEndTime = planEndTime;
        return this;
    }

    public void setPlanEndTime(ZonedDateTime planEndTime) {
        this.planEndTime = planEndTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", taskName='" + getTaskName() + "'" +
            ", taskStatus='" + getTaskStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", planStartTime='" + getPlanStartTime() + "'" +
            ", planEndTime='" + getPlanEndTime() + "'" +
            "}";
    }
}
