package com.sheldonweng.taskmanage.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Date process utils.
 */
public class DateUtils {

    /**
     * Get current zonedDateTime
     *
     * @return
     */
    public static ZonedDateTime getCurrentZonedDateTime() {
        return LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai"));
    }

}
