package assignment2;

import java.io.IOException;

import assignment2.assignment.src.main.java.assignment2.task3.Task3;
import assignment2.assignment.src.main.java.assignment2.task2.Task2;

public class Assignment {
    public static void main(String[] args) throws IOException {
        Task2 task2 = new Task2(1000);
        Task3 task3 = new Task3();

        task2.run();
        task3.run();
    }
}
