package assignment2.assignment.src.main.java.assignment2.task3;

import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

public class Task3 {
    private Event actEvent;
    private State actState;

    private double stdDev;
    private double lowerBound;
    private double upperBound;
    private double mean;
    private List<Double> measuredTimes;
    private SimpleFileWriter f;

    public void run() throws IOException{
        List<Integer> payments = new ArrayList<>(Arrays.asList(5_000, 10_000, 20_000));
        payments.forEach(n -> {
            this.f = new SimpleFileWriter("task3_" + n + ".csv", false);
            IntStream.range(1, 50).forEach(x -> {
                this.measuredTimes = new ArrayList<>();
                this.stdDev = 0.0;
                this.lowerBound = 0.0;
                this.upperBound = 1000.0;
                this.mean = 0.0;

                while (this.stdDev == 0 || (this.upperBound - this.lowerBound) > 2) {
                    this.simLoop(n);
                    
                    this.measuredTimes.add(Global.time);
                    this.mean = this.measuredTimes.stream().mapToDouble(i -> i.doubleValue()).sum() / this.measuredTimes.size();
                    this.stdDev = Math.sqrt(
                            this.measuredTimes.stream().mapToDouble(i -> Math.pow(i - mean, 2)).sum() / this.measuredTimes.size()
                    );
                    double step = 1.96 * this.stdDev / Math.sqrt(this.measuredTimes.size());
                    this.lowerBound = mean - step;
                    this.upperBound = mean + step;

                    Global.time = 0;
                }

                System.out.println("The simulation has ended for a monthly payment of: " + n);
                System.out.println("The standard deviation is: " + this.stdDev);
                System.out.println("The number of measurements: " + this.measuredTimes.size());
                System.out.println("The time is: " + this.measuredTimes.stream().mapToDouble(i -> i.doubleValue()).sum() / this.measuredTimes.size());
                f.println("" + mean);
            });
            this.f.close();
        });
    }

    private void simLoop(int monthlyPayment) {
        this.actState = new State(monthlyPayment);

        Global.insertEvent(Global.INSERT, 0);
        Global.insertEvent(Global.PANIC, Global.uniform(1, 95));

        while (actState.getBalance() < 2_000_000) {
            this.actEvent = Global.eventList.fetchEvent();
            Global.time = this.actEvent.eventTime;
            this.actState.treatEvent(this.actEvent);
        }
    }
}
