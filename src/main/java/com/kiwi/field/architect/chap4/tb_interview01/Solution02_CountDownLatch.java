package com.kiwi.field.architect.chap4.tb_interview01;
/**
 *  * 曾经的面试题：（淘宝？）
 *  * 实现一个容器，提供两个方法，add，size
 *  * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Solution02_CountDownLatch {

	// 添加volatile，使t2能够得到通知
	volatile List lists = new ArrayList();

	public void add(Object o) {
		lists.add(o);
	}

	public int size() {
		return lists.size();
	}

	public static void main(String[] args) {
		Solution02_CountDownLatch c = new Solution02_CountDownLatch();

		// 两个门栓实现线程通信
		CountDownLatch latch1 = new CountDownLatch(1);

		CountDownLatch latch2 = new CountDownLatch(1);
		new Thread(() -> {
			System.out.println("t2启动");
			if (c.size() != 5) {
				try {
					latch1.await();

					//也可以指定等待时间
					//latch1.await(5000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("t2结束");
			latch2.countDown();
		}, "t2").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		new Thread(() -> {
			System.out.println("t1启动");
			for (int i = 0; i < 10; i++) {
				c.add(new Object());
				System.out.println("add " + i);

				if (c.size() == 5) {
					// 打开门闩，让t2得以执行
					latch1.countDown();
					try {
						latch2.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				/*try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
			}

		}, "t1").start();

	}
}
