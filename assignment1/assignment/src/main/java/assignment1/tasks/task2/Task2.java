package assignment1.assignment.src.main.java.assignment1.tasks.task2;

import java.util.*;
import java.io.*;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.GlobalSimulation;


public class Task2 extends GlobalSimulation{
    static Random slump = new Random();
        public void run() throws IOException {
            System.out.println("Task 2");

            Event actEvent;
            TaskState actState = new TaskState(); // The state that shoud be used
            // Some events must be put in the event list at the beginning
            insertEvent(ARRIVALXA, 0);  
            insertEvent(MEASURE, 0.1);
            // insertEvent(READYXA, 0.0    02);
        
        // The main simulation loop
        while (time < 1000){
            actEvent = eventList.fetchEvent();
            time = actEvent.eventTime;
            actState.treatEvent(actEvent);
        }
        
        // Printing the result of the simulation, in this case a mean value
        System.out.println(actState.accumulated);
        System.out.println(actState.noMeasurements);
        System.out.println("mean value: " + 1.0*actState.accumulated/actState.noMeasurements);
        System.out.println("----------------------------------------");
    }
}
