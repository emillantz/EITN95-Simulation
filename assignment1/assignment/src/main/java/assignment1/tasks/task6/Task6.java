package assignment1.assignment.src.main.java.assignment1.tasks.task6;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Task6 {
    private int numIters = 0;
    private List<Part> parts;
    Signal actSignal;
    Machine machine;
    public void run() {
        this.init();
        while (numIters < 1000) {
            actSignal = SignalList.FetchSignal();
            Global.time = actSignal.arrivalTime;

            if (actSignal.signalType == Global.READY) {
                numIters++;
                this.init();
                continue;
            }

            actSignal.destination.TreatSignal(actSignal);
        }
        System.out.println("Simulation finished, + " + numIters + " iterations");
        System.out.println("Mean time: " + Global.time / numIters);
    }

    private void init() {

        new SignalList();
        this.parts = new ArrayList<>();

        Part four = new Part();
        Part three = new Part(Arrays.asList(four));
        
        Part two = new Part();
        Part five = new Part();
        Part one = new Part(Arrays.asList(two, five));

        this.parts.add(one);
        this.parts.add(two);
        this.parts.add(three);
        this.parts.add(four);
        this.parts.add(five);

        this.machine = new Machine(this.parts);

        parts.forEach(part -> {
            part.setMachine(this.machine);
            SignalList.SendSignal(Global.CREATE, part, Global.time);
        });
    }
}
