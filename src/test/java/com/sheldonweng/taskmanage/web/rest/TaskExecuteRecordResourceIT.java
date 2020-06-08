package com.sheldonweng.taskmanage.web.rest;

import com.sheldonweng.taskmanage.SimpleTaskManagementApp;
import com.sheldonweng.taskmanage.domain.TaskExecuteRecord;
import com.sheldonweng.taskmanage.domain.Task;
import com.sheldonweng.taskmanage.repository.TaskExecuteRecordRepository;
import com.sheldonweng.taskmanage.service.TaskExecuteRecordService;
import com.sheldonweng.taskmanage.service.dto.TaskExecuteRecordDTO;
import com.sheldonweng.taskmanage.service.mapper.TaskExecuteRecordMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.sheldonweng.taskmanage.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TaskExecuteRecordResource} REST controller.
 */
@SpringBootTest(classes = SimpleTaskManagementApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskExecuteRecordResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TaskExecuteRecordRepository taskExecuteRecordRepository;

    @Autowired
    private TaskExecuteRecordMapper taskExecuteRecordMapper;

    @Autowired
    private TaskExecuteRecordService taskExecuteRecordService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskExecuteRecordMockMvc;

    private TaskExecuteRecord taskExecuteRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskExecuteRecord createEntity(EntityManager em) {
        TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord()
            .userId(DEFAULT_USER_ID)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        taskExecuteRecord.setTask(task);
        return taskExecuteRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskExecuteRecord createUpdatedEntity(EntityManager em) {
        TaskExecuteRecord taskExecuteRecord = new TaskExecuteRecord()
            .userId(UPDATED_USER_ID)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        // Add required entity
        Task task;
        if (TestUtil.findAll(em, Task.class).isEmpty()) {
            task = TaskResourceIT.createUpdatedEntity(em);
            em.persist(task);
            em.flush();
        } else {
            task = TestUtil.findAll(em, Task.class).get(0);
        }
        taskExecuteRecord.setTask(task);
        return taskExecuteRecord;
    }

    @BeforeEach
    public void initTest() {
        taskExecuteRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskExecuteRecord() throws Exception {
        int databaseSizeBeforeCreate = taskExecuteRecordRepository.findAll().size();
        // Create the TaskExecuteRecord
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(taskExecuteRecord);
        restTaskExecuteRecordMockMvc.perform(post("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeCreate + 1);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTaskExecuteRecord.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTaskExecuteRecord.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTaskExecuteRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskExecuteRecordRepository.findAll().size();

        // Create the TaskExecuteRecord with an existing ID
        taskExecuteRecord.setId(1L);
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(taskExecuteRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskExecuteRecordMockMvc.perform(post("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setUserId(null);

        // Create the TaskExecuteRecord, which fails.
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(taskExecuteRecord);


        restTaskExecuteRecordMockMvc.perform(post("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taskExecuteRecordRepository.findAll().size();
        // set the field null
        taskExecuteRecord.setStartTime(null);

        // Create the TaskExecuteRecord, which fails.
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(taskExecuteRecord);


        restTaskExecuteRecordMockMvc.perform(post("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isBadRequest());

        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaskExecuteRecords() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get all the taskExecuteRecordList
        restTaskExecuteRecordMockMvc.perform(get("/api/task-execute-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskExecuteRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))));
    }
    
    @Test
    @Transactional
    public void getTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        // Get the taskExecuteRecord
        restTaskExecuteRecordMockMvc.perform(get("/api/task-execute-records/{id}", taskExecuteRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskExecuteRecord.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)));
    }
    @Test
    @Transactional
    public void getNonExistingTaskExecuteRecord() throws Exception {
        // Get the taskExecuteRecord
        restTaskExecuteRecordMockMvc.perform(get("/api/task-execute-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // Update the taskExecuteRecord
        TaskExecuteRecord updatedTaskExecuteRecord = taskExecuteRecordRepository.findById(taskExecuteRecord.getId()).get();
        // Disconnect from session so that the updates on updatedTaskExecuteRecord are not directly saved in db
        em.detach(updatedTaskExecuteRecord);
        updatedTaskExecuteRecord
            .userId(UPDATED_USER_ID)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(updatedTaskExecuteRecord);

        restTaskExecuteRecordMockMvc.perform(put("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isOk());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
        TaskExecuteRecord testTaskExecuteRecord = taskExecuteRecordList.get(taskExecuteRecordList.size() - 1);
        assertThat(testTaskExecuteRecord.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTaskExecuteRecord.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTaskExecuteRecord.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskExecuteRecord() throws Exception {
        int databaseSizeBeforeUpdate = taskExecuteRecordRepository.findAll().size();

        // Create the TaskExecuteRecord
        TaskExecuteRecordDTO taskExecuteRecordDTO = taskExecuteRecordMapper.toDto(taskExecuteRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskExecuteRecordMockMvc.perform(put("/api/task-execute-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskExecuteRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskExecuteRecord in the database
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskExecuteRecord() throws Exception {
        // Initialize the database
        taskExecuteRecordRepository.saveAndFlush(taskExecuteRecord);

        int databaseSizeBeforeDelete = taskExecuteRecordRepository.findAll().size();

        // Delete the taskExecuteRecord
        restTaskExecuteRecordMockMvc.perform(delete("/api/task-execute-records/{id}", taskExecuteRecord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskExecuteRecord> taskExecuteRecordList = taskExecuteRecordRepository.findAll();
        assertThat(taskExecuteRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
