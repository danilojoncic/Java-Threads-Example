package org.example.helper;

import lombok.Data;

@Data
public class Timer implements Runnable{
    private final long miliseconds;

    public Timer(long miliseconds) {
        this.miliseconds = miliseconds;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
