package assignment1.assignment.src.main.java.assignment1.tasks.task1;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.MainSimulation;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;

public class Task1 extends MainSimulation {
    public void run() {
        System.out.println("Task 1");


        Event actEvent;
        TaskState actState = new TaskState(); // The state that should be used


        // Some events must be put in the event list at the beginning
        insertEvent(Q1_ARRIVAL, 0);
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
        while (actState.noMeasurements < 1000){
            actEvent = eventList.fetchEvent();
            time = actEvent.eventTime;
            actState.treatEvent(actEvent);
        }
        
        // Printing the result of the simulation, in this case a mean value
        double meanQ1 = 1.0 * actState.accumulatedQ1 /actState.noMeasurements;
        double meanQ2 = 1.0 * actState.accumulatedQ2 /actState.noMeasurements;

        System.out.println("Mean number of customers in queuing system Q1: " + meanQ1);
        System.out.println("Mean number of customers in queuing system Q2: " + meanQ2);
        System.out.println("Rejection: "+ 1.0 * actState.queueRejected /
        actState.queueArrivals);

        System.out.println("----------------------------------------");
    }
}
