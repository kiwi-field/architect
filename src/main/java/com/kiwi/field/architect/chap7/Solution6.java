package com.kiwi.field.architect.chap7;

import java.util.concurrent.CountDownLatch;

/**
 * 解法6: cas解法
 */
public class Solution6 {

    enum ReadyToRun {T1, T2}

    // 确保字母线程先执行
    static volatile ReadyToRun r = ReadyToRun.T2; //思考为什么必须volatile

    public static void main(String[] args) {

        new Thread(() -> {
            // 打印数字
            for (int i = 1; i < 27; i++) {
                while (r != ReadyToRun.T1) {
                }
                System.out.print(i);
                r = ReadyToRun.T2;
            }
        }, "t1").start();

        new Thread(() -> {
            // 打印字母
            for (int i = 65; i < 91; i++) {
                while (r != ReadyToRun.T2) {
                }
                System.out.print((char) i);
                r = ReadyToRun.T1;
            }
        }, "t2").start();
    }
}


