package com.kiwi.field.architect.chap7;

/**
 * 解法3:signal Condition(单个condition)
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Solution4 {
    private static volatile boolean t2Started = false;

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();
                while(!t2Started) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 数字
                for (int i = 1; i < 27; i++) {
                    System.out.print(i);
                    condition.signal();
                    condition.await();
                }

                condition.signal();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }, "t1").start();

        new Thread(()->{
            try {
                lock.lock();
                // 字母
                for (int i = 65; i < 91; i++) {
                    System.out.print((char)i);
                    t2Started = true;
                    condition.signal();
                    condition.await();
                }

                condition.signal();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }, "t2").start();
    }
}


