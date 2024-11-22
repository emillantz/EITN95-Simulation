import java.io.*;
import java.util.*;


public class MainSimulationEvent extends GlobalSimulation{
 
    public static void main(String[] args) throws IOException {
    	Event actEvent;
    	State actState = new State(); // The state that should be used


    	// Some events must be put in the event list at the beginning
        insertEvent(ARRIVALTO1, 0);
        insertEvent(MEASURE, 5);
        
        // The main simulation loop
    	while (actState.noMeasurements < 1000){
    		actEvent = eventList.fetchEvent();
    		time = actEvent.eventTime;
    		actState.treatEvent(actEvent);
    	}
    	
    	// Printing the result of the simulation, in this case a mean value

		System.out.println(1.0 * actState.accumulatedQ1 /actState.noMeasurements);
		System.out.println(1.0 * actState.accumulatedQ2 / actState.noMeasurements);
		System.out.println("rejection "+ 1.0 * actState.queueRejected /
				actState.queueArrivals);

    }
}