package assignment3.assignment.src.main.java.assignment3.tasks.task2;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class Student {
    private int x, y;
    private int id;
    private Direction currentDirection;
    private int velocity;
    private double meetingDuration;
    private boolean inMeeting;
    private boolean hasMoved;
    private Map<Integer, Integer> studentsMet;
    private int squaresLeft;
    private double timeDone;

    public Student(int x, int y, double meetingDuration, int velocity, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.meetingDuration = meetingDuration;
        this.studentsMet = new HashMap<>();
        this.currentDirection = Direction.randomDirection();
        this.squaresLeft = Global.uniform(1, 10);
        if (velocity > 0) {
            this.velocity = velocity;
        } else {
            this.velocity = Global.uniform(1, 7);
        }
    }

    public Coordinates move() {
        if (this.inMeeting) {
            return null;
        }
        Coordinates c = this.directionToCoordinate();

        while (!c.inBounds()) {
            this.setNewDirection();
            c = this.directionToCoordinate();
            this.squaresLeft = Global.uniform(1, 10);
        }

        this.x = c.x;
        this.y = c.y;
        
        this.squaresLeft--;
        if (this.squaresLeft <= 0) {
            this.setNewDirection();
            this.squaresLeft = Global.uniform(1, 10);
        }
        
        return new Coordinates(this.x, this.y);
    }

    public void meet() {
        this.inMeeting = true;
    }

    public void endMeeting(Student buddy) {
        this.inMeeting = false;
        if (buddy != null) {
            int v = this.studentsMet.getOrDefault(buddy.getId(), 0);
            this.studentsMet.put(buddy.getId(), v + 1);
            if (this.isReady()) {
                this.timeDone = Global.time;
            }
        }
    }
    
    private void setNewDirection() {
        this.currentDirection = Direction.randomDirection();
    }

    public Coordinates directionToCoordinate() {
        return switch (this.currentDirection) {
            case NORTH -> new Coordinates(this.x, this.y - 1);
            case NORTHEAST -> new Coordinates(this.x + 1, this.y - 1);
            case EAST -> new Coordinates(this.x + 1, this.y);
            case SOUTHEAST -> new Coordinates(this.x + 1, this.y + 1);
            case SOUTH -> new Coordinates(this.x, this.y + 1);
            case SOUTHWEST -> new Coordinates(this.x - 1, this.y + 1);
            case WEST -> new Coordinates(this.x - 1, this.y);
            case NORTHWEST -> new Coordinates(this.x - 1, this.y - 1);
        };
    }

    public boolean inMeeting() {
        return this.inMeeting; 
    }

    public boolean isReady() {
        return this.studentsMet.values().stream().filter(v -> v > 0).count() == Global.NUM_STUDENTS - 1;
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Coordinates getCoordinates() {
        return new Coordinates(this.x, this.y);
    }

    public int getVelocity() {
        return this.velocity;
    }

    public double getTimeDone() {
        return this.timeDone;
    }

    public Map<Integer, Integer> getStudentsMet() {
        return this.studentsMet;
    }
}
