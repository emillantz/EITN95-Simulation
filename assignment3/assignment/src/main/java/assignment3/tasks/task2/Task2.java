package assignment3.assignment.src.main.java.assignment3.tasks.task2;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.HashMap;

public class Task2 {
    private Signal actSignal;
    private Hall hall;

    private double stdDev;
    private double lowerBound;
    private double upperBound;
    private double mean;
    private List<Double> measuredTimes;
    private SimpleFileWriter f;
    private Map<Integer, List<Integer>> relationships;

    public void run(boolean interval, int iterations) {
        this.relationships = new HashMap<>();
        this.measuredTimes = new ArrayList<>();

        List<Integer> velocities = new ArrayList<>(Arrays.asList(2, 4, -1));
        //List<Integer> velocities = new ArrayList<>(Arrays.asList(2));
            velocities.forEach((v) -> {
                this.f = new SimpleFileWriter("task2_" + v + ".csv", false);
                IntStream.range(0, iterations).forEach((i) -> {
                    Global.time = 0;
                    simLoop(v);
                    if (interval) {
                        calcInterval(v);
                    }
                    System.out.println("Simulation " + i + "finished for velocity: " + v);
                    System.out.println("Time: " + Global.time);
                    this.relationships.forEach((k, val) -> {
                        double t = this.hall.getStudents().get(k).getTimeDone();
                        this.f.print(t + ",");
                        this.f.println(k + "," + val);
                    });
            });
            this.f.close();
        });
    }

    public void simLoop(int velocity) {
        new SignalList();
        this.hall = new Hall(1, velocity);
        
        this.hall.start();

        while (!this.hall.isDone()) {
            actSignal = SignalList.FetchSignal();
            //System.out.println("Signal type: " + actSignal.signalType + " Time: " + actSignal.arrivalTime + " Destination: " + actSignal.destination);
            Global.time = actSignal.arrivalTime;
            actSignal.destination.TreatSignal(actSignal);
        }
        ArrayList<ArrayList<Integer>> relationships = new ArrayList<>();
        for (int i = 0; i < Global.FLOOR_LENGTH; i++) {
            relationships.add(new ArrayList<>());
        }

        this.hall.getStudents().forEach((s) -> {
            this.relationships.put(s.getId(), s.getStudentsMet().values().stream().collect(Collectors.toList()));
        });
    }

    private void calcInterval(int velocity) {
        this.stdDev = 0.0;
        this.lowerBound = 0.0;
        this.upperBound = 1000.0;
        this.mean = 0.0;
        while(this.stdDev == 0 || (this.upperBound - this.lowerBound) > 300) {
            Global.time = 0;
            this.simLoop(velocity);
            this.measuredTimes.add(Global.time);
            this.mean = this.measuredTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            this.stdDev = Math.sqrt(this.measuredTimes.stream().mapToDouble(i -> Math.pow(i - this.mean, 2)).sum() / this.measuredTimes.size());
            double step = 1.96 * this.stdDev / Math.sqrt(this.measuredTimes.size());
            this.lowerBound = this.mean - step;
            this.upperBound = this.mean + step;
            System.out.println("Mean: " + this.mean + " StdDev: " + this.stdDev + " Lower: " + this.lowerBound + " Upper: " + this.upperBound);
        }
    }
}
