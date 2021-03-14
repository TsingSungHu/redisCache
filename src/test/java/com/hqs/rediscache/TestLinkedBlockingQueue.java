package com.hqs.rediscache;

import java.util.concurrent.LinkedBlockingQueue;

public class TestLinkedBlockingQueue {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> linkedBlockingQueue=new LinkedBlockingQueue();
        linkedBlockingQueue.offer(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaaaaa");
            }
        });
    }
}
