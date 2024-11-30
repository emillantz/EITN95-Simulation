import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc {
	public int numberInQueue = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random slump = new Random();

	private double exp(double lambda) {
		double y = slump.nextDouble();
		return (Math.log(1 - y) / (-1 / lambda));
	}

	public void TreatSignal(Signal x) {
		switch (x.signalType) {

			case ARRIVAL: {
				if (numberInQueue == 0) {
					SignalList.SendSignal(READY, this, time + exp(0.5));
				}
				numberInQueue++;
			}
				break;

			case READY: {
				numberInQueue--;
				if (numberInQueue > 0) {
					SignalList.SendSignal(READY, this, time + exp(0.5));
				}
			}
				break;
			case MEASURE: {
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2.0 * slump.nextDouble());
			}
				break;
		}
	}
}
