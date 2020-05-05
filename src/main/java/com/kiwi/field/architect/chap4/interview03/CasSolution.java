package com.kiwi.field.architect.chap4.interview03;

/**
 * @Description CAS解法
 * @Date 2020/4/17 10:19
 * @Author dengxiaoyu
 */
public class CasSolution {

    enum NextRunning
    {
        Letter,
        NUM
    }

    static volatile NextRunning running = NextRunning.Letter;

    public static void main(String[] args) {
        // 字母
        Thread charac = new Thread(() -> {
            for (int i = 65; i < 91; i++) {
                while (running != NextRunning.Letter) {
                    // 一直转圈，轮到自己执行的时候才执行

                }
                System.out.print((char) i);
                running = NextRunning.NUM;
            }
        });

        // 数字
        Thread num = new Thread(() -> {
            for (int i = 1; i < 27; i++) {
                while (running != NextRunning.NUM) {
                    // 一直转圈，轮到自己执行的时候才执行
                }
                System.out.print(i);
                running = NextRunning.Letter;
            }
        });
        num.start();
        charac.start();
    }
}