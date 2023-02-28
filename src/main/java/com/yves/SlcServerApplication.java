package com.yves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class SlcServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlcServerApplication.class, args);
	}

	@EnableAsync
    @Configuration
    class TaskPoolConfig {

		// 針對 @Scheduled 配置裝配不同 taskExecutor (單線/多線)
        @Bean //("taskExecutor")
    	ThreadPoolTaskScheduler taskExecutor() {
            ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
            // 設置ScheduledExecutorService的池大小20。 默認值為1
//            executor.setPoolSize(20); // default one thread
//            executor.setPoolSize(1);
            
            // test
            executor.initialize();
            // test
            
            // ===== 这个方法大多是与schedule方法和shutdown方法搭配使用的 =====
            // true - 在执行shutdown方法后，当前正在等待的任务的和正在运行的任务需要被执行完，然后进程被销毁
            // false - 表示放弃等待的任务，正在运行的任务一旦完成，则进程被销毁
            executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            
            // ===== (測試後沒效果)这个方法大多是与scheduleAtFixedRate方法和scheduleWithFixedDelay方法搭配使用的 =====
            // true - 当是true时，在cheduleAtFisedRate方法和scheduleWithFixedDelay方法提交的任务会继续循环执行
            // false - 在scheduleAtFixedRate方法和scheduleWithFixedDelay方法提交的任务不会被循环执行，但是会将等待的任务执行完毕，然后进程被销毁
//            executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
            
            // 線程池名的前綴
            executor.setThreadNamePrefix("taskExecutor-");
            return executor;
        }

    }
}
