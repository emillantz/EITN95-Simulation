package assignment1.assignment.src.main.java.assignment1.tasks.task5;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Global;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation

public class Task5 extends Global {

    public void run() throws IOException {
        System.out.println("Task 5");
        // The signal list is started and actSignal is declaree. actSignal is the latest
        // signal that has been fetched from the
        // signal list in the main loop below.

        Signal actSignal;
        new SignalList();

        TaskQS Q1 = new TaskQS();
        Q1.sendTo = null;
        TaskQS Q2 = new TaskQS();
        Q2.sendTo = null;
        TaskQS Q3 = new TaskQS();
        Q3.sendTo = null;
        TaskQS Q4 = new TaskQS();
        Q4.sendTo = null;
        TaskQS Q5 = new TaskQS();
        Q5.sendTo = null;

        TaskGen Generator = new TaskGen();
        Generator.lambda = 10;
        Generator.sendToList = new ArrayList<TaskQS>(Arrays.asList(Q1, Q2, Q3, Q4, Q5));
        ;

        // To start the simulation the first signals are put in the signal list

        SignalList.SendSignal(READY, Generator, time);
        SignalList.SendSignal(MEASURE, Q1, time);
        SignalList.SendSignal(MEASURE, Q2, time);
        SignalList.SendSignal(MEASURE, Q3, time);
        SignalList.SendSignal(MEASURE, Q4, time);
        SignalList.SendSignal(MEASURE, Q5, time);

        // This is the main loop

        while (time < 100000) {
            actSignal = SignalList.FetchSignal();
            time = actSignal.arrivalTime;
            actSignal.destination.TreatSignal(actSignal);
        }

        // Finally the result of the simulation is printed below:

        System.out.println("Mean nmbr customers sys 1: " + 1.0 * Q1.accumulated / Q1.noMeasurements);
        System.out.println("Mean nmbr customers sys 2: " + 1.0 * Q2.accumulated / Q2.noMeasurements);
        System.out.println("Mean nmbr customers sys 3: " + 1.0 * Q3.accumulated / Q3.noMeasurements);
        System.out.println("Mean nmbr customers sys 4: " + 1.0 * Q4.accumulated / Q4.noMeasurements);
        System.out.println("Mean nmbr customers sys 5: " + 1.0 * Q5.accumulated / Q5.noMeasurements);
        System.out.println("Mean nmbr customers total: " + 1.0
                * (Q1.accumulated + Q2.accumulated + Q3.accumulated + Q4.accumulated + Q5.accumulated)
                / (Q1.noMeasurements + Q2.noMeasurements + Q3.noMeasurements + Q4.noMeasurements + Q5.noMeasurements));
        System.out.println("----------------------------------------");
    }
}
