package org.example.helper;

import org.example.threads.abstraction.Grader;
import org.example.threads.implementations.Assistent;
import org.example.threads.implementations.Prof;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Keeper {
    public static volatile CyclicBarrier profBarrier = new CyclicBarrier(2);
    public static volatile Semaphore astSemaphore = new Semaphore(1);
    public static volatile Semaphore profSemaphore = new Semaphore(2);
    public static volatile CyclicBarrier astBarrier = new CyclicBarrier(1);
    public static Random random = new Random();
    public static AtomicInteger graded = new AtomicInteger(0);
    public static AtomicInteger totalScore = new AtomicInteger(0);
    public static final Grader prof = new Prof();
    public static final Grader ast = new Assistent();
    public static String profThreadName;
    public static  String astThreadName;


    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss:SSS");
        return simpleDateFormat.format(date);
    }

    public static void printScore(){
        double finalScore = (double)totalScore.get()/ graded.get();
        if(totalScore.get() == 0 && graded.get() == 0){
            finalScore = 0.0;
        }
        System.out.println("Group score: " + finalScore);
        System.out.println(getCurrentTime());
    }
}
