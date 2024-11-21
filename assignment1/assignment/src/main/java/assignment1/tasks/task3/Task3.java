package assignment1.assignment.src.main.java.assignment1.tasks.task3;

import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import assignment1.assignment.src.main.java.assignment1.eventscheduling.Event;
import assignment1.assignment.src.main.java.assignment1.eventscheduling.GlobalSimulation;


public class Task3 extends GlobalSimulation{
    public void run() throws IOException {

        List<Double> means = new ArrayList<>(Arrays.asList(1.1, 1.5, 2.0));


        means.stream().forEach(mean -> {
            System.out.println("Stats for mean: " + mean + "\n");
            simLoop(mean);
        });

}
    private void simLoop(double x) {
        Event actEvent;
        TaskState state = new TaskState(x);

        insertEvent(Q1_ARRIVAL, 0);
        insertEvent(MEASURE, 5);

        while (state.getNbrMeasurements() < 2500) {
            actEvent = eventList.fetchEvent();
            time = actEvent.eventTime;
            state.treatEvent(actEvent);
        }
        double simMeanTime = state.getSimTime() / state.getCompleted();
        double simMeanQueue = state.getAccumulated() / state.getNbrMeasurements();
        double t = state.getT();
        double n = state.getN();

        System.out.println("Mean time (simulated): " + simMeanTime);
        System.out.println("Mean time (theoretical): " + t);
        System.out.println("Diff (sim - theoretical ): " + (simMeanTime - t) + "\n\n");
        System.out.println("Mean number of customers in queue (simulated): " + simMeanQueue);
        System.out.println("Mean number of customers in queue (theoretical): " + n);
        System.out.println("Diff (sim - theoretical): " + (simMeanQueue - n) + "\n\n");
        System.out.println("--------------------------------------------------------");
    }
}
