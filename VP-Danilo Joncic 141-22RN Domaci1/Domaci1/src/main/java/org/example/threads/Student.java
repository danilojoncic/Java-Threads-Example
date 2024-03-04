package org.example.threads;
import lombok.Data;
import org.example.Main;
import org.example.helper.Keeper;
import java.util.concurrent.BrokenBarrierException;

@Data
public class Student implements Runnable {
    private int numberOfAppearance;
    private int arrivalTime;
    private int defenseTime;
    private String grader = "";
    private int score = 0;
    private String name;
    public Student(int numberOfAppearance) {
        this.numberOfAppearance = numberOfAppearance;
        this.arrivalTime = Keeper.random.nextInt(1000);
        this.defenseTime = Keeper.random.nextInt(500, 1000);
    }

    @Override
    public void run() {
        if (Keeper.random.nextBoolean()) {
            try {
                if(Main.getTimer().isAlive()) {
                    grader = "Profesor";
                    Thread.sleep(arrivalTime);
                    Keeper.profBarrier.await();

                    Keeper.prof.grade(this);
                }else{
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("Did not arrive in time!");
            }
        } else {
            try {
                if(Main.getTimer().isAlive()) {
                    grader = "Assistent";
                    Thread.sleep(arrivalTime);
                    Keeper.astBarrier.await();
                    Keeper.ast.grade(this);
                }else{
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("Did not arrive in time!");
            }
        }
    }
}
