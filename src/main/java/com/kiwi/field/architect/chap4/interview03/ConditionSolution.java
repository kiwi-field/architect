package com.kiwi.field.architect.chap4.interview03;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 两个线程循环打印A1B2C3。。。Z26
 * @Date 2020/4/15 10:07
 * @Author dengxiaoyu
 */
public class ConditionSolution {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition charLock = lock.newCondition();
    private static Condition numLock = lock.newCondition();
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        // 字母
        Thread charac = new Thread(() -> {
            countDownLatch.countDown();
            lock.lock();
            for (int i = 65; i < 91; i++) {
                System.out.print((char) i);
                // 通知另一个等待队列上的线程
                numLock.signal();
                try {
                    charLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numLock.signal();
            lock.unlock();

        });

        // 数字
        Thread num = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            for (int i = 1; i < 27; i++) {
                System.out.print(i);
                charLock.signal();
                try {
                    numLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            numLock.signal();
            lock.unlock();
        });
        charac.start();
        num.start();
    }
}