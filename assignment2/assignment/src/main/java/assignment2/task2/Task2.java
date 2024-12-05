package assignment2.assignment.src.main.java.assignment2.task2;

import java.util.stream.IntStream;

import java.util.List;
import java.util.ArrayList;

public class Task2 {
    List<Double> times;
    List<Double> perscriptionTimes;
    Integer nbrDays;

    public Task2(Integer nbrDays) {
        this.times = new ArrayList<>();
        this.perscriptionTimes = new ArrayList<>();

        this.nbrDays = nbrDays;
    }

    public void run() {
        IntStream.range(0, this.nbrDays).forEach(i -> this.simLoop());
        
        System.out.println("--------------------");
        System.out.println("Interval for the average closing time of the pharmacy");
        this.constructInterval(this.times);
        System.out.println("--------------------");
        System.out.println("Interval for the average perscription time");
        this.constructInterval(this.perscriptionTimes);

    }

    private void simLoop() {
        State actState = new State();
        Event actEvent;
        
        double initialTime = Global.poisson();
        Global.time = initialTime;
        Global.insertEvent(Global.ARRIVAL, initialTime);

        while (Global.time < 8 * 60 || actState.getArrivals().size() != 0) {
            actEvent = Global.eventList.fetchEvent();
            Global.time = actEvent.eventTime;
            actState.treatEvent(actEvent);
        }
        actState.getArrivals().forEach(System.out::println);
        this.times.add(Global.time);
        this.perscriptionTimes.addAll(actState.getQueueTimes());
    }

    private void constructInterval(List<Double> iterable) {
        int n = iterable.size();

        double mean = iterable.stream().mapToDouble(x -> x).sum() / n;

        double stdDev = Math.sqrt(iterable.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / n);
        
        double step = 1.96 * stdDev / Math.sqrt(n);

        double lowerBound = mean - step;
        double upperBound = mean + step;

        System.out.println("The simulation has ended for a number of days: " + this.nbrDays);
        System.out.println("The standard deviation is: " + stdDev);
        System.out.println("The number of measurements: " + n);
        System.out.println("The time is: " + mean);
        System.out.println("The lower bound is: " + lowerBound);
        System.out.println("The upper bound is: " + upperBound);
    }
}
