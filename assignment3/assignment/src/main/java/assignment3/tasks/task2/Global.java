package assignment3.assignment.src.main.java.assignment3.tasks.task2;

import java.util.Random;

public class Global {
    static Random r = new Random();

    public static final int NUM_STUDENTS = 20;
    public static final int FLOOR_LENGTH = 20, FLOOR_WIDTH = 20;
    public static final int MOVE = 1, MEET = 2, END_MEET = 3;
    public static double time = 0;

    public static int uniform(int a, int b) {
        return (int) (a + r.nextDouble() * (b - a));
    }
}
