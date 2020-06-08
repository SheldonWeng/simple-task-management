package com.sheldonweng.taskmanage.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import com.sheldonweng.taskmanage.domain.enumeration.TaskStatus;

/**
 * A DTO for the {@link com.sheldonweng.taskmanage.domain.Task} entity.
 */
@ApiModel(description = "Task entity")
public class TaskDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @Size(min = 2, max = 30)
    private String taskName;

    @NotNull
    private TaskStatus taskStatus;

    private String description;

    @NotNull
    private ZonedDateTime createTime;

    private ZonedDateTime planStartTime;

    private ZonedDateTime planEndTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(ZonedDateTime planStartTime) {
        this.planStartTime = planStartTime;
    }

    public ZonedDateTime getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(ZonedDateTime planEndTime) {
        this.planEndTime = planEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
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
