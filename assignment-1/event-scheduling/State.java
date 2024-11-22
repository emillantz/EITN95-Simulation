import java.util.*;
import java.io.*;

class State extends GlobalSimulation{

	// Here follows the state variables and other variables that might be needed
	// e.g. for measurements

	public int nq1 = 0, nq2 = 0, qPlaces = 10, qRejected = 0, qArrivals = 0, noMeasurements = 0,q1 = 0,
			q2 = 0;




	Random slump = new Random(); // This is just a random number generator


	// The following method is called by the main program each time a new event has been fetched
	// from the event list in the main loop.
	public void treatEvent(Event x){
		switch (x.eventType){
			case ARRIVAL:
				arrival1();
				break;
			case DEPART1:
				departure1();
				break;
			case DEPART2:
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
	public double exp(double a) {

		return Math.log(1 -  slump.nextDouble()) / (-a);

	}


	private void arrival1(){
		qArrivals = qArrivals + 1;

		if(nq1 < qPlaces){
			nq1 = nq1 +1;
		}else{
			qRejected = qRejected +1;
		}
		if(qArrivals == 1) {
			insertEvent(DEPART1, time + exp(1/mST1));
		}
		insertEvent(ARRIVAL, time + arrTime);
	}

	private void departure1(){
		nq1--;
		nq2++;

		if(nq2 == 1){
			insertEvent(DEPART2, time + mST2);
		}
		if(nq1 > 0 ) {
			insertEvent(DEPART1, time + exp(1/mST1));
		}
	}
	private void depart2(){
		nq2--;
		if(nq2 >0){
			insertEvent(DEPART2, time + mST2);
		}

	}

	private void ready(){
		//numberInQueue--;
		//if (numberInQueue > 0)
		//	insertEvent(DEPART1,  time + 2*slump.nextDouble());
	}

	private void measure(){
			q1 = q1 + nq1;
			q2 = q2 + nq2;
		noMeasurements++;
		insertEvent(MEASURE, time + exp(mMT));
	}
}