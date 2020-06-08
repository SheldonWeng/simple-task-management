package com.sheldonweng.taskmanage.service.impl;

import com.sheldonweng.taskmanage.domain.TaskExecuteRecord;
import com.sheldonweng.taskmanage.domain.enumeration.TaskStatus;
import com.sheldonweng.taskmanage.exceptions.ServiceWarningException;
import com.sheldonweng.taskmanage.repository.TaskExecuteRecordRepository;
import com.sheldonweng.taskmanage.service.BaseService;
import com.sheldonweng.taskmanage.service.TaskExecuteRecordService;
import com.sheldonweng.taskmanage.service.TaskService;
import com.sheldonweng.taskmanage.domain.Task;
import com.sheldonweng.taskmanage.repository.TaskRepository;
import com.sheldonweng.taskmanage.service.dto.TaskDTO;
import com.sheldonweng.taskmanage.service.mapper.TaskMapper;
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
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
public class TaskServiceImpl extends BaseService<Task> implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskRepository taskRepository;
    @Resource
    private TaskExecuteRecordRepository taskExecuteRecordRepository;
    @Resource
    private TaskExecuteRecordService taskExecuteRecordService;

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        log.debug("Request to get all Tasks");
        return taskRepository.findAll().stream()
            .map(taskMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one task by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id)
            .map(taskMapper::toDto);
    }

    /**
     * Delete the task by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        taskRepository.deleteById(id);
    }

    /**
     * Start doing task
     *
     * @param userId
     * @param taskId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startDoingTask(Long userId, Long taskId) {
        Task task = getNotNullEntity(taskRepository.findById(taskId));

        switch (task.getTaskStatus()) {
            case TODO:
            case PENDING:
                task.setTaskStatus(TaskStatus.DOING);
                taskRepository.save(task);
                taskExecuteRecordRepository.save(new TaskExecuteRecord(task, userId));
                break;
            case DOING:
                throw new ServiceWarningException("Start task failed. The task has already been started.");
            case COMPLETED:
                throw new ServiceWarningException("Start task failed. The task is completed.");
            default:
                throw new ServiceWarningException("Start task failed. The task status is not exists.");
        }
    }

    /**
     * Pause task
     *
     * @param userId
     * @param taskId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pauseTask(Long userId, Long taskId) {
        Task task = getNotNullEntity(taskRepository.findById(taskId));

        switch (task.getTaskStatus()) {
            case TODO:
            case PENDING:
                throw new ServiceWarningException("Pause task failed. The task has not been started.");
            case DOING:
                task.setTaskStatus(TaskStatus.PENDING);
                taskRepository.save(task);
                taskExecuteRecordService.saveEndTime(taskId);
                break;
            case COMPLETED:
                throw new ServiceWarningException("Pause task failed. The task is completed.");
            default:
                throw new ServiceWarningException("Start task failed. The task status is not exists.");
        }
    }

    /**
     * complete task
     *
     * @param userId
     * @param taskId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(Long userId, Long taskId) {
        Task task = getNotNullEntity(taskRepository.findById(taskId));

        switch (task.getTaskStatus()) {
            case TODO:
            case PENDING:
            case DOING:
                task.setTaskStatus(TaskStatus.COMPLETED);
                taskRepository.save(task);
                taskExecuteRecordService.saveEndTime(taskId);
                break;
            case COMPLETED:
                throw new ServiceWarningException("Start task failed. The task is completed.");
            default:
                throw new ServiceWarningException("Start task failed. The task status is not exists.");
        }
    }
}
