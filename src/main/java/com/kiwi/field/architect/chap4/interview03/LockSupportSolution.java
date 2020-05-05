package com.kiwi.field.architect.chap4.interview03;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description 两个线程循环打印A1B2C3。。。Z26
 * @Date 2020/4/16 19:59
 * @Author dengxiaoyu
 */
public class LockSupportSolution {
    static Thread t1 = null;
    static  Thread t2 = null;
    public static void main(String[] args) {
        t1 = new Thread(() -> {
            for (int i = 65; i < 91; i++) {
                System.out.print((char) i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

        t2 = new Thread(() -> {
            for (int i = 1; i < 27; i++) {
                LockSupport.park();
                System.out.print(i);
                LockSupport.unpark(t1);
            }
        });

        t1.start();
        t2.start();
    }
}