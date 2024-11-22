package assignment1.assignment.src.main.java.assignment1.tasks.task6;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Gen;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.List;

public class Machine extends Gen {
    private List<Part> parts;

    public Machine(List<Part> parts) {
        this.parts = parts;
    }

    @Override
    public void TreatSignal(Signal x) {
        if (x.signalType == Global.BREAK) {
            this.breakdown();
        }
    }

    private void breakdown() {
        if (parts.stream().allMatch(part -> part.isBroken())) {
            SignalList.SendSignal(Global.READY, this, Global.time);
        }
    }
}
