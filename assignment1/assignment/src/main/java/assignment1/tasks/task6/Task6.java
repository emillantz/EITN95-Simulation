package assignment1.assignment.src.main.java.assignment1.tasks.task6;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Task6 {

    private int numIters = 0;
    private List<Part> parts;

    public void run() {
        while (numIters < 1000) {
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
    }
}
