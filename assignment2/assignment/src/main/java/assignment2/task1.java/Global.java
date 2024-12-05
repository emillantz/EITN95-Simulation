package assignment2.assignment.src.main.java.assignment2.task2;

public class Global {
public static final int ARRIVAL = 1, FILLED = 2; 
    public static double time = 0;
    public static EventList eventList = new EventList();
    public static void insertEvent(int type, double timeOfEvent) {
        eventList.insertEvent(type, timeOfEvent);
    }
}
