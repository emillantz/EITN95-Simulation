package assignment1.assignment.src.main.java.assignment1.tasks.task6;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Gen;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskGen extends Gen {
    
    private int numIters;
    private List<Part> parts;

    public TaskGen() {
        this.numIters = 0;
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
    }

    @Override
    public void TreatSignal(Signal x) {
        switch (x.signalType) {
            case Global.CREATE -> this.create();
            case Global.BREAK -> this.breakdown();
        }
    }

    private void create() {
        numIters++;
        parts.forEach(part -> {
            SignalList.SendSignal(Global.CREATE, part, Global.time);
        });

    }

    private void breakdown() {
        if (parts.stream().allMatch(part -> part.isBroken())) {
            SignalList.SendSignal(Global.READY, this, Global.time);
        }
    }
}
