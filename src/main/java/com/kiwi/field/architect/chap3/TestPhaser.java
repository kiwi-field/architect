package com.kiwi.field.architect.chap3;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 模拟一个结婚场景，结婚是有好多人要参加的，因此我们写了一个Person，实现了runnable接口，模拟我们每个人要做一些操作，有
 * 几种方法，arrive()到达，eat()吃、leave() 离开、hug()拥抱这么几个。作为一个婚礼来说它会分成好几个阶段，
 * 第1阶段所有人都到齐了
 * 第2阶段所有人都吃饭
 * 第3阶段所有人都离开
 * 第4阶段新郎新娘入洞房
 * 在上述4个阶段中1、2、3阶段需要所有人都到齐，而第4个阶段洞房的事除了新郎新娘，其他人可不能干了
 * 以下程序将整个过程分为好几个阶段，而且每个阶段必须要等这些线程给我干完事儿你才能进入下一个阶段
 */
public class TestPhaser {
    static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();


    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // 指定初始数量为7
        phaser.bulkRegister(7);

        // 启动5个普通客人线程
        for(int i=0; i<5; i++) {

            new Thread(new Person("p" + i)).start();
        }

        // 启动新郎新娘2个线程
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }



    static class MarriagePhaser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            //如果该方法返回true，那么Phaser会被终止。
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }


    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {

            milliSleep(r.nextInt(1000));
            System.out.printf("%s 到达现场！\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
            // 线程到达arriveAndAwaitAdvance方法时会等待前进
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 离开！\n", name);


            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if(name.equals("新郎") || name.equals("新娘")) {
                milliSleep(r.nextInt(1000));
                System.out.printf("%s 洞房！\n", name);
                phaser.arriveAndAwaitAdvance();
            } else {
                // 使当前线程退出，并且是parties值减1
                phaser.arriveAndDeregister();
                //phaser.register()
            }
        }

        @Override
        public void run() {
            arrive();


            eat();


            leave();


            hug();

        }
    }
}