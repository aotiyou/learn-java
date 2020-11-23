package com.zxy.learnjava;

/**
 * TODO
 *
 * @ClassName: Main
 * @author: zxy
 * @since: 2020-11-19 07:37
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        t.join();
        System.out.println("end");
    }

}

class MyThread extends Thread{
    @Override
    public void run() {
        Thread hello = new HelloThread();
        hello.start();
        try{
            hello.join();
        }catch (InterruptedException e){
            System.out.println("interrupted");
        }
        //hello.interrupt();
    }
}

class HelloThread extends Thread {
    @Override
    public void run() {
        int n = 0;
        while (!isInterrupted()){
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            }catch(InterruptedException e){
                break;
            }
        }
    }
}