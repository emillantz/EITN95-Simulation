import java.util.*;
import java.io.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;


//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

		Gateway gateway = new Gateway();
		gateway.sendTo = null;

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values.


		// Parameters, should be read from file
		int n = 1000;
		double tp = 1;
		double ts = 4000;
		double r = 7;
		Sensor[] sensors = new Sensor[n];
		double x, y;
		double ub = 3000;
		double lb = 1000;

		for (int i = 0; i < n; i++) {
			x = Math.random() * n; // Read from file
			y = Math.random() * n; // Read from file
			sensors[i] = new Sensor(x, y, r, ts, tp, gateway);
			sensors[i].lb = lb;
			sensors[i].ub = ub;

			// SignalList.SendSignal(SEND_MESSAGE, sensors[i], time, sensors[i]); //task a and b | no strategy
			SignalList.SendSignal(SEND_CHECK, sensors[i], time, sensors[i]); //task c and d | detection strategy
		}

		SignalList.SendSignal(MEASURE, gateway, time + 10, gateway);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while (time < 10000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
			// System.out.println("here3 and time = " + time);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

		System.out.println("Total messages: " + gateway.totalMessages);
		System.out.println("Simulated Througput: " + (double) gateway.totalSuccess / time);

		double lambda = (double) gateway.totalMessages/time;
		double T_put = lambda * Math.exp(-2*lambda);
		System.out.println("Theoretical Througput: " + T_put);

		System.out.println("Packet Loss: " + (double) gateway.totalCollisions / gateway.totalMessages);
		System.out.println("Confidence Interval: " + gateway.confidenceInterval);

    }
}