package assignment2.assignment.src.main.java.assignment2.task2;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class State {
    private List<Double> fillTimes;
    private LinkedList<Double> arrivals;

    private int queueSize;
    private int numPerscriptions;

    public State() {
        this.fillTimes = new ArrayList<Double>();
        this.arrivals = new LinkedList<Double>();

        this.queueSize = 0;
        this.numPerscriptions = 0;
    }

    public void treatEvent(Event actEvent) {
        switch (actEvent.eventType) {
            case Global.ARRIVAL -> this.arrive();
            case Global.FILLED -> this.fill();
        }
    }
    
    private void arrive() {
        if (Global.time > (8 * 60)) {
            return;
        }
        if (this.arrivals.size() == 0) {
            Global.insertEvent(Global.FILLED, Global.time + Global.uniform(10.0, 20.0));
        }
        arrivals.addLast(Global.time);
        Global.insertEvent(Global.ARRIVAL, Global.time + Global.poisson());
    }
    
    private void fill() {
        fillTimes.add(Global.time - arrivals.poll());

        if (this.arrivals.size() > 0) {
            Global.insertEvent(Global.FILLED, Global.time + Global.uniform(10.0, 20.0));
        }
    }

    public List<Double> getArrivals() {
        return this.arrivals;
    }

    public List<Double> getQueueTimes() {
        return this.fillTimes;
    }
}
