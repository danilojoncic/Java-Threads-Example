package org.example.threads.implementations;
import lombok.Data;
import org.example.Main;
import org.example.helper.Keeper;
import org.example.threads.Student;
import org.example.threads.abstraction.Grader;

@Data
public class Assistent implements Grader {

    @Override
    public void run() {
        System.out.println("Assistent running");
        while(Main.getTimer().isAlive()){}
        System.out.println("AST END");
    }

    @Override
    public void grade(Student student) {
        try {
            Keeper.astSemaphore.acquire();
            student.setScore(Keeper.random.nextInt(5, 10));
            System.out.println("<"+student.getName()+">"+ " Arrival: <"+student.getArrivalTime()+">"
                    +" Grader: <" + student.getGrader() + " " + Keeper.astThreadName + "> TTC: <"+student.getDefenseTime()+ "><" + Keeper.getCurrentTime()+"> Score: <" + student.getScore()+">");
            check();
            Thread.sleep(student.getDefenseTime());
            Keeper.graded.incrementAndGet();
            Keeper.totalScore.addAndGet(student.getScore());
            check();
        } catch (InterruptedException e) {
            System.out.println("Ran out of time while waiting!");
            student.setScore(0);
            Thread.currentThread().interrupt();
        } finally {
            Keeper.astSemaphore.release();
        }
    }

    private void check() {
        if (!Main.getTimer().isAlive()) {
            Thread.currentThread().interrupt();
        }
    }

}
