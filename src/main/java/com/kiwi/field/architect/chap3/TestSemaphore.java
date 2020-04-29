package com.kiwi.field.architect.chap3;

import java.util.concurrent.Semaphore;

/**
 * 如果Semaphore 设置为1,则会等到一个线程都执行完，才能执行另一个线程
 * 如果Semaphore 设置为2，两个线程会同时执行，会出现交替输出的情况
 * new Semaphore(2, true) 第2个参数为true表示是公平，默认Semaphore是非公平的
 */
public class TestSemaphore {
    public static void main(String[] args) {
        //Semaphore s = new Semaphore(2);
//        Semaphore s = new Semaphore(2, true);
        //允许一个线程同时执行
        Semaphore s = new Semaphore(1);

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(()->{
            try {
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");

                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
