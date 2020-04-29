package com.kiwi.field.architect.chap4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if(i == 5) {
                    // 使用LockSupport的park()方法阻塞当前线程t
                    LockSupport.park();
                }

                if (i==8) {
                    // 使用LockSupport的park()方法阻塞当前线程t
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();


//        LockSupport.unpark(t);

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 8 senconds!");

        LockSupport.unpark(t);

    }
}
