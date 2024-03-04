package org.example.threads.abstraction;
import org.example.threads.Student;

public interface Grader extends Runnable{
    public void grade(Student student);

    @Override
    void run();
}
