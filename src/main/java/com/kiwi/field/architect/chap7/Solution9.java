package com.kiwi.field.architect.chap7;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 解法9:pipedStream解法
 */
public class Solution9 {

    public static void main(String[] args) throws Exception {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String msg = "Your Turn";



        new Thread(() -> {

            byte[] buffer = new byte[9];

            try {
                for (int i = 1; i < 27; i++) {
                    input1.read(buffer);

                    if(new String(buffer).equals(msg)) {
                        System.out.print(i);
                    }

                    output1.write(msg.getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "t1").start();

        new Thread(() -> {

            byte[] buffer = new byte[9];

            try {
                for (int i = 65; i < 91; i++) {

                    System.out.print((char)i);

                    output2.write(msg.getBytes());

                    input2.read(buffer);

                    if(new String(buffer).equals(msg)) {
                        continue;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }, "t2").start();
    }
}


