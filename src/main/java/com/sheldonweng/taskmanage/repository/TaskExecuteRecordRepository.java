package com.sheldonweng.taskmanage.repository;

import com.sheldonweng.taskmanage.domain.TaskExecuteRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the TaskExecuteRecord entity.
 */
@Repository
public interface TaskExecuteRecordRepository extends JpaRepository<TaskExecuteRecord, Long> {

    /**
     * Get last execute record for task by taskId.
     *
     * @param taskId
     * @return
     */
    @Query(nativeQuery=true, value = "SELECT * FROM task_execute_record WHERE task_id = ?1 ORDER BY id DESC LIMIT 1 ")
    Optional<TaskExecuteRecord> findLastExecuteRecord(Long taskId);

}

