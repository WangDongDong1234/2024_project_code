package com.wdd.mybatch.core.config.pool;

import com.wdd.mybatch.core.consts.Consts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ScheduleConfiguration {

    /**
     *
     * @return
     */
    @Bean(Consts.ASYNC_TAK_POOL)
    public ThreadPoolTaskExecutor asyncTaskPool(){
        // 创建线程池任务执行器对象
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数量
        executor.setCorePoolSize(8);
        // 设置最大线程数量
        executor.setMaxPoolSize(16);
        // 设置阻塞队列容量
        //executor.setQueueCapacity(256);
        // 设置线程空闲时间，默认为 60 秒
        executor.setKeepAliveSeconds(60);
        // 设置是否支持回收核心线程，默认为 false
        executor.setAllowCoreThreadTimeOut(false);
        // 设置线程名称前缀，若不设置则根据对象的 beanName 生成
        executor.setThreadNamePrefix("async-task-thread-");
        executor.setThreadGroupName("async-task-pool");
        // 设置线程池拒绝策略，默认为 AbortPolicy，即线程数量达到最大线程数量，且阻塞队列容量已满，再添加任务则抛出异常。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //跨线程传递ThreadLocal的方案
        //executor.setTaskDecorator(TraceRunnable::get);
        //该方法用来设置 线程池关闭 的时候 等待 所有任务都完成后，再继续 销毁 其他的 Bean，这样这些 异步任务 的 销毁 就会先于 数据库连接池对象 的销毁。
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //setAwaitTerminationSeconds(60): 该方法用来设置线程池中 任务的等待时间，如果超过这个时间还没有销毁就 强制销毁，以确保应用最后能够被关闭，而不是阻塞住。
        executor.setAwaitTerminationSeconds(60);
        // 初始化
        executor.initialize();
        return executor;
    }


}
