package com.sheldonweng.taskmanage.service.mapper;


import com.sheldonweng.taskmanage.domain.*;
import com.sheldonweng.taskmanage.service.dto.TaskExecuteRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskExecuteRecord} and its DTO {@link TaskExecuteRecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface TaskExecuteRecordMapper extends EntityMapper<TaskExecuteRecordDTO, TaskExecuteRecord> {

    @Mapping(source = "task.id", target = "taskId")
    TaskExecuteRecordDTO toDto(TaskExecuteRecord taskExecuteRecord);

    @Mapping(source = "taskId", target = "task")
    TaskExecuteRecord toEntity(TaskExecuteRecordDTO taskExecuteRecordDTO);

    default TaskExecuteRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord();
        taskExecuteRecord.setId(id);
        return taskExecuteRecord;
    }
}
