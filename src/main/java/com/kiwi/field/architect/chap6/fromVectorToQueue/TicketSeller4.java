/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * 
 * 分析下面的程序可能会产生哪些问题？
 * 重复销售？超量销售？
 * 
 * 使用Vector或者Collections.synchronizedXXX
 * 分析一下，这样能解决问题吗？
 * 
 * 就算操作A和B都是同步的，但A和B组成的复合操作也未必是同步的，仍然需要自己进行同步
 * 就像这个程序，判断size和进行remove必须是一整个的原子操作
 * 
 * 使用ConcurrentQueue提高并发性
 * 
 * @author 马士兵
 */
package com.kiwi.field.architect.chap6.fromVectorToQueue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.LongAdder;

public class TicketSeller4 {
	static Queue<String> tickets = new ConcurrentLinkedQueue<>();
	private static LongAdder longAdder = new LongAdder();

	static {
		for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread[] threads = new Thread[10];
		for(int i=0; i<10; i++) {
			threads[i] = new Thread(() -> {
				while (true) {
					String s = tickets.poll();
					if (s == null) break;
					else {
						longAdder.increment();
						System.out.println("销售了--" + s);
					}
				}
			});
			threads[i].start();
		}

		for (Thread thread : threads) {
			thread.join();
		}
		System.out.println("一共销售了多少张票?"+ longAdder.longValue());
	}
}
