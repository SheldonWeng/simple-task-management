package com.sheldonweng.taskmanage.service;

import com.sheldonweng.taskmanage.domain.TaskExecuteRecord;
import com.sheldonweng.taskmanage.exceptions.CommonExceptions;
import com.sheldonweng.taskmanage.repository.TaskExecuteRecordRepository;
import com.sheldonweng.taskmanage.service.dto.TaskExecuteRecordDTO;
import com.sheldonweng.taskmanage.service.mapper.TaskExecuteRecordMapper;
import com.sheldonweng.taskmanage.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TaskExecuteRecord}.
 */
@Service
@Transactional
public class TaskExecuteRecordService {

    private final Logger log = LoggerFactory.getLogger(TaskExecuteRecordService.class);

    @Resource
    private TaskExecuteRecordRepository taskExecuteRecordRepository;
    @Resource
    private TaskExecuteRecordMapper taskExecuteRecordMapper;

    /**
     * Save a taskExecuteRecord.
     *
     * @param taskExecuteRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskExecuteRecordDTO save(TaskExecuteRecordDTO taskExecuteRecordDTO) {
        log.debug("Request to save TaskExecuteRecord : {}", taskExecuteRecordDTO);
        TaskExecuteRecord taskExecuteRecord = taskExecuteRecordMapper.toEntity(taskExecuteRecordDTO);
        taskExecuteRecord = taskExecuteRecordRepository.save(taskExecuteRecord);
        return taskExecuteRecordMapper.toDto(taskExecuteRecord);
    }

    /**
     * Get all the taskExecuteRecords.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskExecuteRecordDTO> findAll() {
        log.debug("Request to get all TaskExecuteRecords");
        return taskExecuteRecordRepository.findAll().stream()
            .map(taskExecuteRecordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskExecuteRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskExecuteRecordDTO> findOne(Long id) {
        log.debug("Request to get TaskExecuteRecord : {}", id);
        return taskExecuteRecordRepository.findById(id)
            .map(taskExecuteRecordMapper::toDto);
    }

    /**
     * Delete the taskExecuteRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskExecuteRecord : {}", id);
        taskExecuteRecordRepository.deleteById(id);
    }

    /**
     * Save endTime for taskExecuteRecord
     *
     * @param taskId
     */
    public void saveEndTime(Long taskId) {
        log.debug("Request to save endTime for taskExecuteRecord : {}", taskId);
        TaskExecuteRecord executeRecord = taskExecuteRecordRepository.findLastExecuteRecord(taskId)
            .orElseThrow(() -> CommonExceptions.ENTITY_IS_NOT_FOUND_BY_ID);

        executeRecord.setEndTime(DateUtils.getCurrentZonedDateTime());
        taskExecuteRecordRepository.save(executeRecord);
    }

}
