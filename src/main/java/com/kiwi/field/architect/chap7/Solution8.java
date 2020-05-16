package com.kiwi.field.architect.chap7;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 解法8: BlockingQueue解法， 利用put 和take阻塞方法特性，
 * put的时候满了就会阻塞住,take的时候如果没有，就会阻塞在这等着
 * 一个线程输出玩了就打印自己的状态为ok,通知另一个线程可以执行了
 */
public class Solution8 {


    static BlockingQueue<String> q1 = new ArrayBlockingQueue(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue(1);

    public static void main(String[] args) throws Exception {

        new Thread(() -> {

            for (int i = 65; i < 91; i++) {
                System.out.print((char) i);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t1").start();

        new Thread(() -> {

             for (int i = 1; i < 27; i++){
                try {
                    q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(i);
                try {
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t2").start();


    }
}


