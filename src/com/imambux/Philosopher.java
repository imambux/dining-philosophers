package com.imambux;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher implements Runnable {

  private int id;
  // volatile boolean
  private AtomicBoolean full;
  private Chopstick leftChopstick;
  private Chopstick rightChopstick;
  private Random random;
  private int eatingCounter;

  public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
    this.id = id;
    this.leftChopstick = leftChopstick;
    this.rightChopstick = rightChopstick;
    this.random = new Random();
    this.full = new AtomicBoolean(false);
  }

  @Override
  public void run() {
    try {
      // after eating a lot (between 0-1000 ms), we terminate the given thread
      while (!full.get()) {
        think();
        if (leftChopstick.pickUp(this, ChopstickState.LEFT)) {
          // the philosopher is able to acquire the left chopstick
          if (rightChopstick.pickUp(this, ChopstickState.RIGHT)) {
            // the philosopher is able to acquire the right chopstick
            eat();
            rightChopstick.putDown(this, ChopstickState.RIGHT);
            leftChopstick.putDown(this, ChopstickState.LEFT);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void think() throws InterruptedException {
    System.out.printf("%s is thinking...%n", this);
    // Philosopher thinks for a random time between 0-1000 ms
    Thread.sleep(random.nextInt(1000));
  }

  private void eat() throws InterruptedException {
    System.out.printf("%s is eating...%n", this);
    eatingCounter++;
    // Philosopher eats for a random time between 0-1000 ms
    Thread.sleep(random.nextInt(1000));
  }

  public AtomicBoolean getFull() {
    return full;
  }

  public void setFull(AtomicBoolean full) {
    this.full = full;
  }

  @Override
  public String toString() {
    return "Philosopher{" +
        "id=" + id +
        '}';
  }

  public int getEatingCounter() {
    return this.eatingCounter;
  }
}
