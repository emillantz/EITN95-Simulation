import java.util.*;
import java.io.*;

//Denna klass �rver Proc, det g�r att man kan anv�nda time och signalnamn utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation 

class Gen extends Proc{

	//Slumptalsgeneratorn startas:
	//The random number generator is started:
	Random slump = new Random();

	//Generatorn har tv� parametrar:
	//There are two parameters:
	public Proc sendTo;    //Anger till vilken process de genererade kunderna ska skickas //Where to send customers
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