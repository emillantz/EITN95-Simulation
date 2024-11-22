package assignment1.assignment.src.main.java.assignment1.tasks.task2;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.GlobalSimulation;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;

import java.util.*;
import java.io.*;
import java.math.*;

class TaskState extends GlobalSimulation{
	private double xa = 0.002, xb = 0.004, lambda = 1.0/150.0;
	private double d = 1.0;
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberOfXAInQueue = 0, accumulated = 0, noMeasurements = 0, numberOfXBInQueue = 0;
	Random slump = new Random(); // This is just a random number generator

	// Event dummy = eventList.fetchEvent();
	private double exp(double lamdba) {
		double y = slump.nextDouble();
		return (-lambda)*Math.log(1 - y);
	}
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVALXA:
				arrivalXA();
				break;
			case ARRIVALXB:
				arrivalXB();
				break;
			case READYXA:
				readyXA();
				break;
			case READYXB:
				readyXB();
				break;
			case MEASURE:
				measure();
				break;
		}
	}
	
	
	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if 
	// things are getting more complicated than this.
	
	private void arrivalXA(){
		if (numberOfXAInQueue + numberOfXBInQueue == 0)
			insertEvent(READYXA, time + xa);
		numberOfXAInQueue++;
		insertEvent(ARRIVALXA, time + exp(lambda));

	}

	private void arrivalXB() {
		if (numberOfXAInQueue + numberOfXBInQueue == 0)
			insertEvent(READYXB, time + xb);
		numberOfXBInQueue++;
		// insertEvent(ARRIVALXB, time + d);
	}
	
	private void readyXA(){
		numberOfXAInQueue--;
		// insertEvent(ARRIVALXB, time + d); //constant delay distribution
		insertEvent(ARRIVALXB, time + exp(d)); //exponential delay distribution
		
		//priorty B first
		if (numberOfXBInQueue > 0) {
			insertEvent(READYXB, time + xb);
		} else if (numberOfXAInQueue > 0) {
			insertEvent(READYXA, time + xa);
		} 

		//priorty A first
		// if (numberOfXAInQueue > 0) {
		// 	insertEvent(READYXA, time + xa);
		// } else if (numberOfXBInQueue > 0) {
		// 	insertEvent(READYXB, time + xb);
		// } 
	}

	private void readyXB() {
		numberOfXBInQueue--;
		
		//priorty B first
		if (numberOfXBInQueue > 0) {
			insertEvent(READYXB, time + xb);
		} else if (numberOfXAInQueue > 0) {
			insertEvent(READYXA, time + xa);
		} 
		
		//priorty A first
		// if (numberOfXAInQueue > 0) {
		// 	insertEvent(READYXA, time + xa);
		// } else if (numberOfXBInQueue > 0) {
		// 	insertEvent(READYXB, time + xb);
		// } 
	}
	
	private void measure(){	
		accumulated = accumulated + numberOfXAInQueue + numberOfXBInQueue;
		// System.out.println("number in XA queue " + numberOfXAInQueue + " number in XB queue " + numberOfXBInQueue);
		noMeasurements++;

		try (FileWriter fw = new FileWriter("output.dat", true)) {
			fw.write(time + " " + (numberOfXAInQueue + numberOfXBInQueue) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		insertEvent(MEASURE, time + 0.1);
	}
}
