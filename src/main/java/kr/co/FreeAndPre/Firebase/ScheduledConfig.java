package kr.co.FreeAndPre.Firebase;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class ScheduledConfig {

    private final int POOL_SIZE = 20;

    public TaskScheduler scheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);

        return scheduler;
    }
}
