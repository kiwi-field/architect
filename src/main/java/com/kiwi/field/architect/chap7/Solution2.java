package com.kiwi.field.architect.chap7;

import java.util.concurrent.CountDownLatch;

/**
 * 解法2： sync+wait+notify+countDownLatch
 * @Description 两个线程循环打印A1B2C3。。。Z26 countDownLatch保证线程顺序
 * @Date 2020/4/15 10:07
 * @Author dengxiaoyu
 */
public class Solution2 {

    private static Object lock = new Object();
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        // 字母 65-91 对应 A-Z
        new Thread(() -> {
            countDownLatch.countDown();
            synchronized (lock) {
                for (int i = 65; i < 91; i++) {
                    System.out.print((char) i);
                    // notify不释放锁
                    lock.notify();
                    // 通过wait释放锁
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 确保最后执行的线程释放掉锁
                lock.notify();
            }
        }).start();

        // 数字
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (lock) {
                for (int i = 1; i < 27; i++) {
                    System.out.print(i);
                    // notify不释放锁
                    lock.notify();
                    try {
                        // 通过wait释放锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 确保最后执行的线程释放掉锁
                lock.notify();
            }
        }).start();
    }
}