package assignment1.assignment.src.main.java.assignment1.tasks.task4;

import assignment1.assignment.src.main.java.assignment1.processinteraction.QS;
import assignment1.assignment.src.main.java.assignment1.processinteraction.Signal;
import assignment1.assignment.src.main.java.assignment1.processinteraction.SignalList;

import java.util.LinkedList;

public class TaskQS extends QS {
    public int numberSpecial = 0,accumulatedSpecial = 0, arrival = 0,accumulatedRegular = 0; 
    
    private double mST = 4;
	public double probability;

	public double exp(double d) {
			double rand = slump.nextDouble();
			double difference = 1 - rand;
		return -Math.log(difference) / d;
	}


	public double special = 0;
	public double regular  = 0;
	LinkedList<Double>timeS = new LinkedList<Double>();
	LinkedList<Double>timeR = new LinkedList<Double>();



    @Override
    public void TreatSignal(Signal x){
            switch (x.signalType){
                case ARRIVAL:{
                    arrival++;

                    if(slump.nextDouble()<probability){
                        numberSpecial++;
                        accumulatedSpecial++;
                        timeS.addLast(time);
                    }else{
                        accumulatedRegular++;
                        timeR.addLast(time);
                    }
                    numberInQueue++;
                    if (numberInQueue == 1){
                        SignalList.SendSignal(READY,this, time +exp(1/mST));
                    }
                } break;

                case READY:{

                    if (numberSpecial > 0){
                        numberSpecial--;
                        numberInQueue--;

                        special += (time - timeS.removeFirst());
                    }else{
                        numberInQueue--;
                        regular += (time - timeR.removeFirst());
                    }
                    if (numberInQueue > 0){
                        SignalList.SendSignal(READY, this, time + exp(1/mST));
                    }
                } break;

                case MEASURE:{
                    noMeasurements++;
                    accumulated = accumulated + numberInQueue;
                    SignalList.SendSignal(MEASURE, this, time + 100*slump.nextDouble());
                } break;
            }
        }
}
