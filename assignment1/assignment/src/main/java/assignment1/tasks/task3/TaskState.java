package assignment1.assignment.src.main.java.assignment1.tasks.task3;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.State;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;

import java.util.Stack;

import java.util.List;
import java.util.ArrayList;

class TaskState extends State {
    private final int MEASURE_TIME_MEAN = 5, SERVICE_TIME_MEAN = 1;;


    private double simTime = 0;
    private double x;

    private int q1Size, q2Size, completed;

    private List<Double> arrivals = new ArrayList<>();

    
    public TaskState(double x) {
        this.x = x;
        this.q1Size = 0;
        this.q2Size = 0;
        this.completed= 0;
    }

    @Override
    public void treatEvent(Event x) {
        switch (x.eventType) {
            case Q1_ARRIVAL -> q1Arrive();
            case Q2_ARRIVAL -> q2Arrive();
            case Q1_READY -> q1Depart();
            case Q2_READY -> q2Depart();
            case MEASURE -> measure();
        }
    }

    //Lecture 3
    private double exp(double lambda) {
        double y = slump.nextDouble();
        return (-lambda)*Math.log(1 - y);
        
    }

    private void q1Arrive() {
        if (this.q1Size == 0) {
            insertEvent(Q1_READY, time + exp(SERVICE_TIME_MEAN));
        }
        this.q1Size++;
        insertEvent(Q1_ARRIVAL, time + exp(x));

        arrivals.add(time);
    }

    private void q2Arrive() {
        if (this.q2Size == 0) {
            insertEvent(Q2_READY, time + exp(SERVICE_TIME_MEAN));
        }
        this.q2Size++;
    }

    private void q1Depart() {
        this.q1Size--;
        insertEvent(Q2_ARRIVAL, time);

        if (this.q1Size > 0) {
            insertEvent(Q1_READY, time + exp(SERVICE_TIME_MEAN));
        }
    }

    private void q2Depart() {
        this.q2Size--;

        if (this.q2Size > 0) {
            insertEvent(Q2_READY, time + exp(SERVICE_TIME_MEAN));
        }

        double arrivalTime = arrivals.remove(0);
        this.simTime += time - arrivalTime;
        this.completed++;
    }

    private void measure() {
        this.accumulated += this.q1Size + this.q2Size;
        this.noMeasurements++;
        insertEvent(MEASURE, time + exp(MEASURE_TIME_MEAN));
    }

    public int getNbrMeasurements() {
        return this.noMeasurements;
    }

    public int getCompleted() {
        return this.completed;
    }

    public double getSimTime() {
        return this.simTime;
    }

    public int getAccumulated() {
        return this.accumulated;
    }

    public double getN() {
        return 2 / (this.x - 1);
    }

    public double getT() {
        return (this.x * 2) / (this.x - 1);
    }
}
