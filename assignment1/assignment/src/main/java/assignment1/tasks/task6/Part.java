package assignment1.assignment.src.main.java.assignment1.tasks.task6;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Proc;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Part extends Proc {

    public enum state {
        WORKING, 
        BROKEN, 
        IDLE,
    }
    
    private Random r = new Random();
    private boolean broken = false;
    private List<Part> nodes;

    public Part() {
        nodes = new ArrayList<>();
    }

    public Part(List<Part> nodes) {
        this.nodes = nodes;
    }
    
    @Override
    public void TreatSignal(Signal x) {
        switch (x.signalType) {
           case Global.CREATE -> this.create();
           case Global.BREAK -> this.breakdown();
        }
    }

    private int u() {
        return this.r.nextInt(5) + 1; //Discrete uniform distribution (0-5)
    }    

    private void create() {
        if (this.broken) {
            this.broken = false;
        }
        SignalList.SendSignal(Global.BREAK, this, Global.time + u());
    }

    private void breakdown() {
        if (this.broken) {
            return;
        }
        this.broken = true;
        
        nodes.forEach(child -> {
            SignalList.SendSignal(Global.BREAK, child, Global.time);
        });
    }

    public boolean isBroken() {
        return this.broken;
    }
}
