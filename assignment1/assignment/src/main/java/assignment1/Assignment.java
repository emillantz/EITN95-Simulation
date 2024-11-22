package assignment1;

import assignment1.assignment.src.main.java.assignment1.tasks.task1.Task1;
import assignment1.assignment.src.main.java.assignment1.tasks.task3.Task3;
import assignment1.assignment.src.main.java.assignment1.tasks.task6.Task6;

public class Assignment {
    public static void main(String[] args) throws Exception {
        Task1 task1 = new Task1();
        Task3 task3 = new Task3();
        Task6 task6 = new Task6();


        task1.run();
        task3.run();
        task6.run();
    }
}
