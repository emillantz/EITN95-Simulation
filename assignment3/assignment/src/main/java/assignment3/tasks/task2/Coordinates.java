package assignment3.assignment.src.main.java.assignment3.tasks.task2;

public class Coordinates {
    public int x, y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean inBounds() {
        return this.x >= 0 && this.x < Global.FLOOR_WIDTH && this.y >= 0 && this.y < Global.FLOOR_LENGTH;
    }
}
