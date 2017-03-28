package com.mkahramana;

import java.util.concurrent.Semaphore;

public class main {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[]{new Semaphore(1),
                new Semaphore(1), new Semaphore(1), new Semaphore(1), new Semaphore(1)};
        Semaphore multiplex = new Semaphore(4);
        for (int i = 0; i < 5; i++) {
            (new Philosopher(forks, multiplex, i)).start();
        }
    }
}