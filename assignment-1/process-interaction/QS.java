import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	public int numberInQueue = 0,numberSpecial = 0,accumulatedSpecial = 0,
			arrival = 0,accumulatedRegular = 0, accumulated, noMeasurements;
	public Proc sendTo;
	Random slump = new Random();
	public double probability;

	public double expRandom(double d){
		return Math.log(1-slump.nextDouble()/(-d));
	}

	public double special = 0;
	public double regular  = 0;
	LinkedList<Double>timeS = new LinkedList<Double>();
	LinkedList<Double>timeR = new LinkedList<Double>();


	public void TreatSignal(Signal x){
		switch (x.signalType){
			case ARRIVAL:{
				arrival++;

				if(slump.nextDouble()<probability){
					numberSpecial++;
					accumulatedSpecial++;
					timeS.addLast(time);
				}else{
					accumulatedRegular++;
					timeR.addLast(time);
				}
				numberInQueue++;
				if (numberInQueue == 1){
					SignalList.SendSignal(READY,this, time +expRandom(0.25));
				}
			} break;

			case READY:{
				numberInQueue--;
				if (numberSpecial > 0){
					numberSpecial--;
					numberInQueue--;


				}
				if (numberInQueue > 0){
					SignalList.SendSignal(READY, this, time + 0.2*slump.nextDouble());
				}
			} break;

			case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break;
		}
	}
}