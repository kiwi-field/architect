package com.kiwi.field.architect.chap7;

/**
 * 解法3:sync+wait+notify+cas
 */
public class Solution3 {

    private static volatile boolean t2Started = false;

    //private static CountDownLatch latch = new C(1);


    public static void main(String[] args) {
        final Object o = new Object();
        new Thread(()->{
            //latch.await();

            synchronized (o) {

                while(!t2Started) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 打印数字
                for (int i = 1; i < 27; i++) {
                    System.out.print(i);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                o.notify();
            }
        }, "t1").start();

        new Thread(()->{

            synchronized (o) {
                // 打印字母
                for (int i = 65; i < 91; i++) {
                    System.out.print((char)i);
                    //latch.countDown()
                    t2Started = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();
    }
}


