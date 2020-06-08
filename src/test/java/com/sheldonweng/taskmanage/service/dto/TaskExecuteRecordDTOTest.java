package com.sheldonweng.taskmanage.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sheldonweng.taskmanage.web.rest.TestUtil;

public class TaskExecuteRecordDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskExecuteRecordDTO.class);
        TaskExecuteRecordDTO taskExecuteRecordDTO1 = new TaskExecuteRecordDTO();
        taskExecuteRecordDTO1.setId(1L);
        TaskExecuteRecordDTO taskExecuteRecordDTO2 = new TaskExecuteRecordDTO();
        assertThat(taskExecuteRecordDTO1).isNotEqualTo(taskExecuteRecordDTO2);
        taskExecuteRecordDTO2.setId(taskExecuteRecordDTO1.getId());
        assertThat(taskExecuteRecordDTO1).isEqualTo(taskExecuteRecordDTO2);
        taskExecuteRecordDTO2.setId(2L);
        assertThat(taskExecuteRecordDTO1).isNotEqualTo(taskExecuteRecordDTO2);
        taskExecuteRecordDTO1.setId(null);
        assertThat(taskExecuteRecordDTO1).isNotEqualTo(taskExecuteRecordDTO2);
    }
}
