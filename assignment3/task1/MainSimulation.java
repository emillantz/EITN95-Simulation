import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    private class Coordinate {
        public double x;
        public double y;

        public Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private class Config {
        public int n;
        public double tp;
        public double ts;
        public double r;
        public double ub;
        public double lb;
        public Sensor[] sensors;
        public List<Coordinate> coordinates;

        public Config(String path) {
            parseCsv(path);
        }

        private void parseCsv(String path) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path));
                String[] values = lines.get(1).split(",");
                this.n = Integer.parseInt(values[0]);
                this.tp = Double.parseDouble(values[1]);
                this.ts = Double.parseDouble(values[2]);
                this.r = Double.parseDouble(values[3]);
                this.lb = Double.parseDouble(values[4]);
                this.ub = Double.parseDouble(values[5]);
                this.sensors = new Sensor[n];
                this.coordinates = lines.stream().skip(2).map(line -> {
                    String[] vals = line.split(",");
                    return new Coordinate(Double.parseDouble(vals[0]), Double.parseDouble(vals[1]));
                }).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

        Config config = new Config("config.csv");

		int n = config.n;
		double tp = config.tp;
		double ts = config.ts;
		double r = config.r;
		Sensor[] sensors = new Sensor[n];
		double x, y;
		double lb = config.lb;
		double ub = config.ub;
        List<Coordinate> coordinates = config.coordinates;

		for (int i = 0; i < n; i++) {
            Coordinate coord = coordinates.get(i);
			x = coord.x; // Read from file
			y = coord.y; // Read from file
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
