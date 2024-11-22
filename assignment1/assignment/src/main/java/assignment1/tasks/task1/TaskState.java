package assignment1.assignment.src.main.java.assignment1.tasks.task1;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.State;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;

public class TaskState extends State {
	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements

	public int numberInQ1 = 0, numberinQ2 = 0, q1MaxPlaces = 10, queueRejected = 0, queueArrivals = 0,
			noMeasurements = 0,accumulatedQ1 = 0,
			accumulatedQ2 = 0;


	//Random slump = new Random(); // This is just a random number generator


	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x){
		switch (x.eventType){
			case Q1_ARRIVAL:
				arrival1();
				break;
			case Q1_READY:
				ready();
				break;
			case Q2_READY:
				depart2();
				break;
			case MEASURE:
				measure();
				break;
		}
	}


	// The following methods defines what should be done when an event takes place. This could
	// have been placed in the case in treatEvent, but often it is simpler to write a method if
	// things are getting more complicated than this.


	private double arrTime = 1;
	private double mST1 = 2.1; //mean service time 1
	private double mST2 = 2.0; //mean service time 2
	private double mMT = 5.0;
	public double exp(double d) {

			double rand = slump.nextDouble();
		return -Math.log(1 - rand) / d;
	}



	private void arrival1(){
		queueArrivals = queueArrivals + 1;

		if(numberInQ1 < q1MaxPlaces){
			numberInQ1++;
		}else{
			queueRejected++;
		}
		if(queueArrivals == 1) {
			insertEvent(Q1_READY, time + exp(1/mST1));
		}
		insertEvent(Q1_ARRIVAL, time + arrTime);
	}

	private void ready(){
		numberInQ1--;
		numberinQ2++;
		double d = (1/mST1);

		if(numberinQ2 == 1){
			insertEvent(Q2_READY, time + mST2);
		}
		if(numberinQ2 > 0 ) {
			insertEvent(Q1_READY, time + exp(d));
		}
	}
	private void depart2(){
		numberinQ2--;
		if(numberinQ2 >0){
			insertEvent(Q2_READY, time + mST2);
		}

	}



	private void measure(){
			accumulatedQ1 += numberInQ1;
			accumulatedQ2 += numberinQ2;
			noMeasurements++;
		insertEvent(MEASURE, time + exp(mMT));
	}
}
