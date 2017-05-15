package com.ztesoft.zsmart.ci.jacoco;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 持久化处理线程池
 * @author chm
 *
 */
public class C3JPAActionThreadPool4Exec {

    /**
     * 线程池 ,任务队列是一个无限队列，类似于生产者消费者的模式
     */
    private static ThreadPoolExecutor pool;

    static {
        pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);
    }

    /** 线程池实例化对象 */
    private static C3JPAActionThreadPool4Exec threadPool = new C3JPAActionThreadPool4Exec();

    /**
     * 获取线程池对象实例
     * 
     * @return SocketThreadPool
     */
    public static C3JPAActionThreadPool4Exec getInstance() {
        if (threadPool == null) {
            threadPool = new C3JPAActionThreadPool4Exec();
        }
        return threadPool;
    }

    /**
     * 发起任务
     * 
     * @param persistenceTask 任务
     */
    public void execCommand(Runnable persistenceTask) {
        pool.execute(persistenceTask);
    }

}
