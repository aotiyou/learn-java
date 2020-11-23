package com.itranswarp.learnjava;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("main start...");
//		new Thread1().start();
//		new Thread2().start();
//		for (int i = 0; i < 100; i++) {
//			System.out.println("main: running...");
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//			}
//		}
		Thread thread1 = new Thread1();
		Thread thread2 = new Thread2();
		thread2.start();
		thread1.start();
		thread1.join();
		System.out.println("main end...");


	}

}

class Thread1 extends Thread {

	public void run() {
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread-1: running...");

//		for (int i = 0; i < 100; i++) {
//			System.out.println("Thread-1: running...");
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//			}
//		}
	}
}

class Thread2 extends Thread {

	public void run() {
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread-2: running...");

//		for (int i = 0; i < 100; i++) {
//			System.out.println("Thread-2: running...");
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//			}
//		}
	}
}
