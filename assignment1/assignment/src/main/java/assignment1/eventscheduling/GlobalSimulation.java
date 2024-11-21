package assignment1.assignment.src.main.java.assignment1.eventscheduling;

public class GlobalSimulation{
	
	// This class contains the definition of the events that shall take place in the
	// simulation. It also contains the global time, the event list and also a method
	// for insertion of events in the event list. That is just for making the code in
	// MainSimulation.java and State.java simpler (no dot notation is needed).
    
	// The events, add or remove if needed!
	public static final int Q1_ARRIVAL = 1, Q1_READY = 2, Q2_ARRIVAL = 3, Q2_READY = 4, MEASURE = 5; 
	public static double time = 0; // The global time variable
	public static EventListClass eventList = new EventListClass(); // The event list used in the program
	public static void insertEvent(int type, double TimeOfEvent){  // Just to be able to skip dot notation
		eventList.InsertEvent(type, TimeOfEvent);
	}
}
