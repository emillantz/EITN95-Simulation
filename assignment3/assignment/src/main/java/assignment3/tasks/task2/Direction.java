package assignment3.assignment.src.main.java.assignment3.tasks.task2;

public enum Direction {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public static Direction randomDirection() {
        return values()[(int) (Math.random() * values().length)];
    }
}
