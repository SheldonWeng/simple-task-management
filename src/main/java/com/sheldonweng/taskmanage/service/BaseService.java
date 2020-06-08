package com.sheldonweng.taskmanage.service;

import com.sheldonweng.taskmanage.exceptions.CommonExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * BaseService
 * Define common service methods.
 *
 * @author Sheldon Weng
 */
public class BaseService<T> {

    private final Logger log = LoggerFactory.getLogger(BaseService.class);

    /**
     * Validate and return not-null entity.
     *
     * @param entity
     * @return
     */
    public T getNotNullEntity(T entity) {
        log.debug("get not null entity : {}", entity);
        return Optional.ofNullable(entity).orElseThrow(() -> CommonExceptions.ENTITY_IS_NOT_FOUND_BY_ID);
    }

    /**
     * Validate and return not-null entity.
     *
     * @param entity
     * @return
     */
    public T getNotNullEntity(Optional<T> entity) {
        log.debug("get not null entity : {}", entity);
        return entity.orElseThrow(() -> CommonExceptions.ENTITY_IS_NOT_FOUND_BY_ID);
    }

}
