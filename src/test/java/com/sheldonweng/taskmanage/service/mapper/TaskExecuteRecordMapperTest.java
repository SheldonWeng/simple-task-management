package com.sheldonweng.taskmanage.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskExecuteRecordMapperTest {

    private TaskExecuteRecordMapper taskExecuteRecordMapper;

    @BeforeEach
    public void setUp() {
        taskExecuteRecordMapper = new TaskExecuteRecordMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskExecuteRecordMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskExecuteRecordMapper.fromId(null)).isNull();
    }
}
