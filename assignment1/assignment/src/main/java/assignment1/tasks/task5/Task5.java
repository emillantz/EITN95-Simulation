import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class MainSimulation extends Global {

	public static void main(String[] args) throws IOException {

		// The signal list is started and actSignal is declaree. actSignal is the latest
		// signal that has been fetched from the
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		QS Q1 = new QS();
		Q1.sendTo = null;
		QS Q2 = new QS();
		Q2.sendTo = null;
		QS Q3 = new QS();
		Q3.sendTo = null;
		QS Q4 = new QS();
		Q4.sendTo = null;
		QS Q5 = new QS();
		Q5.sendTo = null;

		Gen Generator = new Gen();
		Generator.mean = 0.15;
		Generator.sendToList = new ArrayList<QS>(Arrays.asList(Q1, Q2, Q3, Q4, Q5));

		// To start the simulation the first signals are put in the signal list

		SignalList.SendSignal(READY, Generator, time);
		SignalList.SendSignal(MEASURE, Generator, 0);
		SignalList.SendSignal(MEASURE, Q1, 0);
		SignalList.SendSignal(MEASURE, Q2, 0);
		SignalList.SendSignal(MEASURE, Q3, 0);
		SignalList.SendSignal(MEASURE, Q4, 0);
		SignalList.SendSignal(MEASURE, Q5, 0);

		// This is the main loop

		while (time < 100000) {
			actSignal = SignalList.FetchSignal();
			time = actSignal.arrivalTime;
			actSignal.destination.TreatSignal(actSignal);
		}

		// Finally the result of the simulation is printed below:

		System.out.println(
				"Total and numberOfMeasurments: " + Generator.totalInQueues + " " + Generator.numberOfMeasurements);
		System.out.println(
				"Total Mean numbr of customers: " + 1.0 * Generator.totalInQueues / Generator.numberOfMeasurements);

		System.out.println(
				"Mean nmbr customers sys 1: " + 1.0 * Generator.numberInQueues.get(0) / Generator.numberOfMeasurements);
		System.out.println("Mean nmbr customers sys 2: " + 1.0 * Q2.accumulated / Q2.noMeasurements);
		System.out.println("Mean nmbr customers sys 3: " + 1.0 * Q3.accumulated / Q3.noMeasurements);
		System.out.println("Mean nmbr customers sys 4: " + 1.0 * Q4.accumulated / Q4.noMeasurements);
		System.out.println("Mean nmbr customers sys 5: " + 1.0 * Q5.accumulated / Q5.noMeasurements);
	}
}
