package com.imambux;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Chopstick {

  private int id;
  // lock the chopstick so that only one philosopher acquires the current chopstick
  private Lock lock;

  public Chopstick(int id) {
    this.id = id;
    this.lock = new ReentrantLock();
  }

  public boolean pickUp(Philosopher philosopher, ChopstickState chopstickState)
      throws InterruptedException {
    // try to get the chopstick within 10 ms if available
    if (lock.tryLock(10, TimeUnit.MILLISECONDS)) {
      System.out.printf("%s pickup up %s %s%n", philosopher, chopstickState.toString(), this);

      return true;
    }

    return false;
  }

  public void putDown(Philosopher philosopher, ChopstickState chopstickState) {
    lock.unlock();
    System.out.printf("%s puts down %s %s%n", philosopher, chopstickState.toString(), this);
  }

  @Override
  public String toString() {
    return "Chopstick{" +
        "id=" + id +
        '}';
  }
}
