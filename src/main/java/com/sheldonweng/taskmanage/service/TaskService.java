package com.sheldonweng.taskmanage.service;

import com.sheldonweng.taskmanage.service.dto.TaskDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sheldonweng.taskmanage.domain.Task}.
 */
public interface TaskService {

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Get all the tasks.
     *
     * @return the list of entities.
     */
    List<TaskDTO> findAll();


    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskDTO> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Start doing task
     *
     * @param userId
     * @param taskId
     */
    void startDoingTask(Long userId, Long taskId);

    /**
     * pause task
     *
     * @param userId
     * @param taskId
     */
    void pauseTask(Long userId, Long taskId);

    /**
     * complete task
     *
     * @param userId
     * @param taskId
     */
    void completeTask(Long userId, Long taskId);

}
