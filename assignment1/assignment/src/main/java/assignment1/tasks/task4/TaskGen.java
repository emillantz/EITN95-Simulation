package assignment1.assignment.src.main.java.assignment1.tasks.task4;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Gen;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

public class TaskGen extends Gen {
    

    public double lambda = 0.2;  //Hur m�nga per sekund som ska generas //How many to generate per second

	public double exp(double d) {
			double rand = slump.nextDouble();
			double logValue = Math.log(1 - rand);
		return -logValue / d;
	}


	//H�r nedan anger man vad som ska g�ras n�r en signal kommer //What to do when a signal arrives

	public void TreatSignal(Signal x) {
		switch (x.signalType) {
			case READY: {
				SignalList.SendSignal(ARRIVAL, sendTo, time);
				SignalList.SendSignal(READY, this, time + exp(lambda));

			}
			break;
		}
	}
	//(2.0/lambda)*slump.nextDouble())

}
