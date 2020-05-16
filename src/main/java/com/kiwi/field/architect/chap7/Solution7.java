package com.kiwi.field.architect.chap7;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解法8: AtomicInteger解法
 */
public class Solution7 {

    static AtomicInteger threadNo = new AtomicInteger(2);

    public static void main(String[] args) {
        // 打印数字的线程
        new Thread(() -> {

            for (int i = 1; i < 27; i++) {
                while (threadNo.get() != 1) {}
                System.out.print(i);
                threadNo.set(2);
            }

        }, "t1").start();

        // 打印字母的线程
        new Thread(() -> {

            for (int i = 65; i < 91; i++) {
                while (threadNo.get() != 2) {
                }
                System.out.print((char) i);
                threadNo.set(1);
            }
        }, "t2").start();
    }
}


