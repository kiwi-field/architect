package com.kiwi.field.architect.chap7;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class Solution10 {
    public static void main(String[] args) {
        TransferQueue<Object> queue = new LinkedTransferQueue<>();
        new Thread(()->{
            try {
                for (int i = 1; i < 27; i++) {
                    System.out.print(queue.take());
                    queue.transfer(i+"");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(()->{
            try {
                for (int i = 65; i < 91; i++) {
                    queue.transfer((char)i);
                    System.out.print(queue.take());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
