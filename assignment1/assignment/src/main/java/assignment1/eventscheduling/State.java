package assignment1.assignment.src.main.java.assignment1.eventscheduling;

import java.util.*;
import java.io.*;

public class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberInQueue = 0, accumulated = 0, noMeasurements = 0;

	public Random slump = new Random(); // This is just a random number generator
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case Q1_ARRIVAL:
				arrival();
				break;
			case Q1_READY:
				ready();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrival(){
		if (numberInQueue == 0)
			insertEvent(Q1_READY, time + 2*slump.nextDouble());
		numberInQueue++;
		insertEvent(Q1_ARRIVAL, time + 2.5*slump.nextDouble());
	}
	
	private void ready(){
		numberInQueue--;
		if (numberInQueue > 0)
			insertEvent(Q1_READY, time + 2*slump.nextDouble());
	}
	
	private void measure(){
		accumulated = accumulated + numberInQueue;
		noMeasurements++;
		insertEvent(MEASURE, time + slump.nextDouble()*10);
	}
}
