package com.feng.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(fixedDelay = 60000)
    public void sayHello() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        LOGGER.info("当前系统可用最大内存{}MB, 总内存{}MB, 可用内存{}MB", maxMemory / 1024 / 1024, totalMemory / 1024 / 1024, freeMemory / 1024 / 1024);
    }

    @Scheduled(fixedDelay = 60000)
    private void showTime () {
        LOGGER.info("当前系统时间：{}, {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), System.getProperty("os"));
    }
}
