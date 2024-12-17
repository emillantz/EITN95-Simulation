package assignment3.assignment.src.main.java.assignment3.tasks.task1;

import java.util.*;

public class Sensor extends Proc {

    public double x, y, radius, lambda, messageTime, lb, ub;
    private static Random slump = new Random();

    public Proc sendTo;
    
    public Sensor (double x, double y, double radius, double lambda, double messageTime, Proc sendTo) {
        this.x = x;
        this.y = y;
        this.radius = radius*1000;
        this.lambda = lambda;
        this.messageTime = messageTime;
        this.sendTo = sendTo;
    }

    public static double expDist(double lambda) {
        return (-lambda) * Math.log(1 - slump.nextDouble());
    }

    // public boolean checkCollision(Sensor s1, Sensor s2) {
    //     double distance = Math.sqrt(Math.pow(s1.x - s2.x, 2) + Math.pow(s1.y - s2.y, 2));
    //     return distance < s1.radius + s2.radius;
    // }

    
    public boolean checkCollision(Sensor s1, Sensor s2) {
        double xDiff = Math.pow(s2.x - s1.x,2);
		double yDiff = Math.pow(s2.y - s1.y, 2);
		
		return Math.sqrt(xDiff + yDiff) < s2.radius;
    }

    private double uniformSleep() {
        return lb + (ub - lb) * slump.nextDouble();
    }

    @Override
    public void TreatSignal(Signal x) {
        switch (x.signalType) {
            case SEND_MESSAGE: {
                SignalList.SendSignal(RECEIVE_TRANSMISSION, sendTo, time, this);
                SignalList.SendSignal(SEND_MESSAGE, this, time + messageTime + expDist(lambda), this);
            } break;
            case SEND_CHECK: {
                SignalList.SendSignal(RECEIVE_TRANSMISSION, sendTo, time, this);
                SignalList.SendSignal(CHECK_CHANNEL, this, time + messageTime + expDist(lambda), this);
            } break;
            case CHECK_CHANNEL: {
                for (Map.Entry<Sensor, Boolean> entry : Gateway.sensors.entrySet()) {
                    if (this.checkCollision(this, entry.getKey())) {
                        SignalList.SendSignal(CHECK_CHANNEL, this, time + uniformSleep(), this);
                        return;
                    }
                }
                SignalList.SendSignal(SEND_CHECK, this, time, this);
            } break;
        }
    }

}
