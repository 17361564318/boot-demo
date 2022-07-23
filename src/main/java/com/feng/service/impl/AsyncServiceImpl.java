package com.feng.service.impl;

import com.feng.configuration.VisiableThreadPoolTaskExecutor;
import com.feng.service.AsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;


/**
 * @author fhn
 * @create 2021/7/12
 * @software idea
 */
@Service
public class AsyncServiceImpl implements AsyncService {
    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Autowired
    @Qualifier(value = "asyncServiceExecutor")
    private Executor executor;

    @Override
    @Async("asyncServiceExecutor")
    public void executorAsyncOne() {
        logger.info("start executorAsyncOne");
        String threadName = Thread.currentThread().getName();
        long start = System.currentTimeMillis();
        List<Integer> list = new ArrayList<>(16);
        for (int i = 0; i < 100000; i++) {
            list.add( i);
        }
        long end = System.currentTimeMillis();
        logger.info("线程{} ：{}存入数据消耗了{}毫秒", threadName, list.getClass().getSimpleName(), (end - start));
        logger.info("end executorAsyncOne");
    }

    @Override
    @Async(value = "asyncServiceExecutor")
    public void executorAsyncTwo() {
        logger.info("start executorAsyncTwo");
        String threadName = Thread.currentThread().getName();
        long start = System.currentTimeMillis();
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add( i);
        }
        long end = System.currentTimeMillis();
        logger.info("线程{} ：{}存入数据消耗了{}毫秒", threadName, list.getClass().getSimpleName(), (end - start));
        logger.info("end executorAsyncTwo");
        executorThree();
    }

    public void executorThree() {
        Future<?> future = ((ThreadPoolTaskExecutor) executor).submit(() -> System.out.println("class :"));
        try {
            Object o = future.get();
        } catch (Exception e) {
            logger.error("", e);
        }
    }
}
