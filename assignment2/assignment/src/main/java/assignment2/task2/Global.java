package assignment2.assignment.src.main.java.assignment2.task2;

import java.util.Random;

public class Global {
    private static Random r = new Random();

    public static final int ARRIVAL = 1, FILLED = 2; 
    public static double time = 0;
    public static EventList eventList = new EventList();
    public static void insertEvent(int type, double timeOfEvent) {
        eventList.insertEvent(type, timeOfEvent);
    }
    
    // This works by seeing poisson as an inverse Exp.
    public static double poisson() {
        double lambda = 15;
        double x = r.nextDouble();
        return (-lambda) * Math.log(1 - x);
    }

    public static double uniform(double a, double b) {
        return (a + r.nextDouble() * (b - a));
    }
}
