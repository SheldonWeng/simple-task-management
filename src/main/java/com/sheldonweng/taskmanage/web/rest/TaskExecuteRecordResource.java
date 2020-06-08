package com.sheldonweng.taskmanage.web.rest;

import com.sheldonweng.taskmanage.service.TaskExecuteRecordService;
import com.sheldonweng.taskmanage.service.UserService;
import com.sheldonweng.taskmanage.web.rest.errors.BadRequestAlertException;
import com.sheldonweng.taskmanage.service.dto.TaskExecuteRecordDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sheldonweng.taskmanage.domain.TaskExecuteRecord}.
 */
@RestController
@RequestMapping("/api")
public class TaskExecuteRecordResource {

    private final Logger log = LoggerFactory.getLogger(TaskExecuteRecordResource.class);

    private static final String ENTITY_NAME = "taskExecuteRecord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Resource
    private TaskExecuteRecordService taskExecuteRecordService;

    /**
     * {@code POST  /task-execute-records} : Create a new taskExecuteRecord.
     *
     * @param taskExecuteRecordDTO the taskExecuteRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskExecuteRecordDTO, or with status {@code 400 (Bad Request)} if the taskExecuteRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-execute-records")
    public ResponseEntity<TaskExecuteRecordDTO> createTaskExecuteRecord(@Valid @RequestBody TaskExecuteRecordDTO taskExecuteRecordDTO) throws URISyntaxException {
        log.debug("REST request to save TaskExecuteRecord : {}", taskExecuteRecordDTO);
        if (taskExecuteRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskExecuteRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskExecuteRecordDTO result = taskExecuteRecordService.save(taskExecuteRecordDTO);
        return ResponseEntity.created(new URI("/api/task-execute-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-execute-records} : Updates an existing taskExecuteRecord.
     *
     * @param taskExecuteRecordDTO the taskExecuteRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskExecuteRecordDTO,
     * or with status {@code 400 (Bad Request)} if the taskExecuteRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskExecuteRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-execute-records")
    public ResponseEntity<TaskExecuteRecordDTO> updateTaskExecuteRecord(@Valid @RequestBody TaskExecuteRecordDTO taskExecuteRecordDTO) throws URISyntaxException {
        log.debug("REST request to update TaskExecuteRecord : {}", taskExecuteRecordDTO);
        if (taskExecuteRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskExecuteRecordDTO result = taskExecuteRecordService.save(taskExecuteRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskExecuteRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-execute-records} : get all the taskExecuteRecords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskExecuteRecords in body.
     */
    @GetMapping("/task-execute-records")
    public List<TaskExecuteRecordDTO> getAllTaskExecuteRecords() {
        log.debug("REST request to get all TaskExecuteRecords");
        return taskExecuteRecordService.findAll();
    }

    /**
     * {@code GET  /task-execute-records/:id} : get the "id" taskExecuteRecord.
     *
     * @param id the id of the taskExecuteRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskExecuteRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-execute-records/{id}")
    public ResponseEntity<TaskExecuteRecordDTO> getTaskExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to get TaskExecuteRecord : {}", id);
        Optional<TaskExecuteRecordDTO> taskExecuteRecordDTO = taskExecuteRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskExecuteRecordDTO);
    }

    /**
     * {@code DELETE  /task-execute-records/:id} : delete the "id" taskExecuteRecord.
     *
     * @param id the id of the taskExecuteRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-execute-records/{id}")
    public ResponseEntity<Void> deleteTaskExecuteRecord(@PathVariable Long id) {
        log.debug("REST request to delete TaskExecuteRecord : {}", id);
        taskExecuteRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
