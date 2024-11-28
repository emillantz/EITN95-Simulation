package assignment1.assignment.src.main.java.assignment1.tasks.task5;

import assignment1.assignment.src.main.java.assignment1.processinteraction.Proc;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.*;
import java.io.*;

//It inherits Proc so that we can use time and the signal names without dot notation 

class TaskGen extends Proc{


    //The random number generator is started:
    Random slump = new Random();
    public ArrayList<TaskQS> sendToList = new ArrayList<>();


    //There are two parameters:
    private Proc sendTo;    //Where to send customers
    public double lambda;  
    private int count = 0;
    //What to do when a signal arrives
    @Override
    public void TreatSignal(Signal x){
        switch (x.signalType){
            case READY:{
                sendTo = roundRobin();
                SignalList.SendSignal(ARRIVAL, sendTo, time);
                SignalList.SendSignal(READY, this, time + 2 * lambda *slump.nextDouble());}
                break;
        }
    }

    private Proc random() {
        return sendToList.get(slump.nextInt(5));
    }

    private Proc roundRobin() {
        count++;
        if (count > 4) count = 0;
        return sendToList.get(count);
    }

    private Proc smallestFirst() {
        int min = Integer.MAX_VALUE;
        ArrayList<TaskQS> minList = new ArrayList<>();
        for (TaskQS q : sendToList) {
            if (q.numberInQueue < min) {
                min = q.numberInQueue;
                
            }
        }
        for (TaskQS q : sendToList) {
            if(q.numberInQueue == min) {
                minList.add(q);
            }
        }
        return minList.get(slump.nextInt(minList.size()));
    }
}
