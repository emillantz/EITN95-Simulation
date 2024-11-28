package assignment2.assignment.src.main.java.assignment2.task3;

import java.util.Random;

public class Global {
    private static Random r = new Random();

    public static final double GROWTH_RATE = Math.pow(1.3, 1d/12); //Annual interest is 30%
    public static final int INSERT = 1, PANIC = 2; 
    public static double time = 0;
    public static EventList eventList = new EventList();
    public static void insertEvent(int type, double timeOfEvent) {
        eventList.insertEvent(type, timeOfEvent);
    }

    public static int uniform(int a, int b) {
        return (int)(a + r.nextDouble() * (b - a));
    }
}
