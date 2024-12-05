import java.util.*;
import java.io.*;
import java.math.*;;

class State extends GlobalSimulation{
	
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements
	public int numberOfCustomers = 0, accumulated = 0, noMeasurements = 0;

	Random slump = new Random(); // This is just a random number generator
	
	int N = 100;
	int x = 10;
	int lambda = 4;
	public int T = 1;

	private double poisson(double lambda) {
		double y = slump.nextDouble();
		return -Math.log(1 - y)/lambda;
	}
	
	
	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop. 
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival();
				break;
			case READY:
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
		if (numberOfCustomers < N) {
			insertEvent(READY, time + x);
			numberOfCustomers++;
		}
		insertEvent(ARRIVAL, time + poisson(lambda));
	}
	
	private void ready(){
		numberOfCustomers--;
	}
	
	private void measure(){
		accumulated = accumulated + numberOfCustomers;
		noMeasurements++;
		try (FileWriter fw = new FileWriter("output.dat", true)) {
			fw.write(time + " " + numberOfCustomers + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		insertEvent(MEASURE, time + T);
	}
}