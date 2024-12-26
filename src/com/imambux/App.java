package com.imambux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = null;
    Philosopher[] philosophers = null;
    Chopstick[] chopsticks = null;

    try {
      philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
      chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

      // initialize all chopsticks
      for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
        chopsticks[i] = new Chopstick(i);
      }

      executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);

      for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
        philosophers[i] = new Philosopher(
          i,
          chopsticks[i],
          // to handle the last philosopher who should have chopstick 0 if the left chopstick is 4
          chopsticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICKS]
        );

        executorService.execute(philosophers[i]);
      }

      Thread.sleep(Constants.SIMULATION_RUNNING_TIME_IN_MS);

      for (Philosopher philosopher : philosophers) {
        philosopher.setFull(new AtomicBoolean(true));
      }
    } finally {
      executorService.shutdown();

      while (!executorService.isTerminated()) {
        Thread.sleep(1000);
      }

      for (Philosopher philosopher : philosophers) {
        System.out.printf("%s eat #%d times%n", philosopher, philosopher.getEatingCounter());
      }
    }
  }

}
