package org.example;
import lombok.Data;
import org.example.helper.Keeper;
import org.example.helper.Timer;
import org.example.threads.Student;
import java.util.*;
import java.util.concurrent.*;

@Data
public class Main {
    static final Thread timer = new Thread(new Timer(5000));
    public static void main(String[] args) {
        System.out.println("Enter number of students: ");
        Scanner scanner = new Scanner(System.in);

        int studentNumber = scanner.nextInt();


        ExecutorService executor = Executors.newFixedThreadPool(studentNumber + 2);
        Map<Student,Thread> students = new HashMap<>();
        Thread profThread = new Thread(Keeper.prof);
        Thread astThread = new Thread(Keeper.ast);

        Keeper.profThreadName = profThread.getName();
        Keeper.astThreadName = astThread.getName();

        //prije se ovdje submit za prof i ast thredove nalazio ali to nije imalo smisla

        for (int i = 0; i < studentNumber; i++) {
            Student student = new Student(i+1);
            Thread studentThread = new Thread(student);
            student.setName(studentThread.getName());
            students.put(student, studentThread);
        }
        System.out.println(Keeper.getCurrentTime());
        timer.start();

        //jako bitno da sam ovo ovdje premjestio jer prije sam napravio gresku
        executor.submit(profThread);
        executor.submit(astThread);

        for (Thread studentThread : students.values()) {
            executor.submit(studentThread);
        }
        System.out.println("Defending has started!");
        try {
            timer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdownNow();
        Keeper.printScore();
    }
    public static Thread getTimer() {
        return timer;
    }

}
