package assignment3.assignment.src.main.java.assignment3.tasks.task1;

import java.util.*;
import java.util.Map.Entry;

public class Gateway extends Proc {
    public Proc sendTo;
    public int totalMessages = 0, totalCollisions = 0, totalSuccess = 0, noMeasurements = 0;
    public ArrayList<Double> packetLosses = new ArrayList<>();
    public static HashMap<Sensor, Boolean> sensors = new HashMap<>();
    boolean collision = false;
    Random slump = new Random();

    public double confidenceInterval = 1.0;

    public boolean checkCollision(Sensor s1, Sensor s2) {
        double distance = Math.sqrt(Math.pow(s1.x - s2.x, 2) + Math.pow(s1.y - s2.y, 2));
        return distance < s1.radius + s2.radius;
    }

    public double confidenceInterval(ArrayList<Double> data) {
        double sum = 0;
        for (double d : data) {
            sum += d;
        }
        double mean = sum / data.size();

        double sumOfSquares = 0;
        for (double d : data) {
            sumOfSquares += Math.pow(d - mean, 2);
        }
        double variance = sumOfSquares / data.size();

        return 1.96 * Math.sqrt(variance / data.size());
    }

    @Override
    public void TreatSignal(Signal x) {
        switch (x.signalType) {
            case RECEIVE_TRANSMISSION: {
                totalMessages++;
                sensors.put((Sensor) x.source, false);
                SignalList.SendSignal(TRANSMISSION_END, this, time + ((Sensor)x.source).messageTime, x.source);
            } break;

            case TRANSMISSION_END: {
                collision = sensors.remove((Sensor) x.source);
                for (Entry<Sensor, Boolean> entry : sensors.entrySet()) {
                    entry.setValue(true);
                    collision = true;
                }

                if (collision) {
                    totalCollisions++;
                } else {
                    totalSuccess++;
                }
            } break;

            case MEASURE: {
                noMeasurements++;
                double packetLoss = (double) totalCollisions / totalMessages;
                packetLosses.add(packetLoss);
                confidenceInterval = confidenceInterval(packetLosses);
                SignalList.SendSignal(MEASURE, this, time + 2 * slump.nextDouble(), this);
            } break;
        }
    }
}
