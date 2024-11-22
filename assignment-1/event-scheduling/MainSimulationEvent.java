import java.io.*;
import java.util.*;


public class MainSimulationEvent extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used


    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVAL, 0);  
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value

		System.out.println("N1 = " + 1.0 * actState.q1 /actState.noMeasurements);
		System.out.println("N2 = " + 1.0 * actState.q2 / actState.noMeasurements);
		System.out.println("Rejection probability = " + 1.0 * actState.qRejected /
				actState.qArrivals);

    }
}