package assignment2.assignment.src.main.java.assignment2.task3;

public class State {
    
    private int monthlyPayment;
    private double balance;

    public State(int monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
        this.balance = 0;
    }
    

    public double getBalance() {
        return this.balance;
    }

    public void treatEvent(Event actEvent) {
        switch (actEvent.eventType) {
            case Global.INSERT -> this.insert();
            case Global.PANIC -> this.panic();
        }
    }
    
    private double TimeUniformForDisturbance() {
        return Global.time + Global.uniform(1, 95); // Uniform(1, 95) results in the expected value of 4 years as specified
    }

    private void insert() {
        this.balance *= Global.GROWTH_RATE;
        this.balance += this.monthlyPayment;

        Global.insertEvent(Global.INSERT, Global.time + 1);
    }
    
    private void panic() {
       int percentage = Global.uniform(0, 100);
       this.matchPercentage(percentage);
       Global.insertEvent(Global.PANIC, TimeUniformForDisturbance());
    }

    private void matchPercentage(int percentage) {
       if (percentage < 10) {
           this.balance *= 0.75;
           return;
       } 
       if (percentage < 35) {
           this.balance *= 0.5;
           return;
       } 
       if (percentage < 50) {
           this.balance *= 0.4;
           return;
       } 
       this.balance *= 0.9;
       return;
       
    }
}
