package com.mkahramana;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore multiplex;
    private int philosopherID;

    public Philosopher(Semaphore[] forks, Semaphore multiplex, int philosopherID) {
        this.forks = forks;
        this.multiplex = multiplex;
        this.philosopherID = philosopherID;
    }

    private int leftFork() {
        return philosopherID % 5;
    }

    private int rightFork() {
        return (philosopherID + 1) % 5;
    }

    private void pickUp() throws InterruptedException {
        multiplex.acquire();
        forks[leftFork()].acquire();
        forks[rightFork()].acquire();
        monitor("Philosopher " + philosopherID + ": picked up " + leftFork() + "-" + rightFork() + ".forks");
    }

    private void eat() throws InterruptedException {
        monitor("Philosopher " + philosopherID + ": is eating");
    }

    private void putDown() {
        forks[leftFork()].release();
        forks[rightFork()].release();
        multiplex.release();
        monitor("Philosopher " + philosopherID + ": put down " + leftFork() + "-" + rightFork() + ".forks");
    }

    private void monitor(String msg) {
        System.out.println(msg);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                pickUp();
                eat();
                putDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}