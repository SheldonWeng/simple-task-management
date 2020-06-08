package com.sheldonweng.taskmanage.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sheldonweng.taskmanage.domain.TaskExecuteRecord} entity.
 */
@ApiModel(description = "Task execute record entity")
public class TaskExecuteRecordDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private ZonedDateTime startTime;

    private ZonedDateTime endTime;


    private Long taskId;
    
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

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskExecuteRecordDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskExecuteRecordDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskExecuteRecordDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", taskId=" + getTaskId() +
            "}";
    }
}
