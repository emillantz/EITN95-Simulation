import java.util.*;
import java.io.*;


public class MainSimulation extends GlobalSimulation{
    static Random slump = new Random();
        public static void main(String[] args) throws IOException {
            Event actEvent;
            State actState = new State(); // The state that shoud be used
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
    }
}