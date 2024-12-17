package assignment3.assignment.src.main.java.assignment3.tasks.task2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Hall extends Proc {
    private List<Student> students;
    private double meetingDuration;
    private int velocity;

    public Hall(double meetingDuration, int velocity) {
        this.students = new ArrayList<>();
        this.meetingDuration = meetingDuration;
        this.velocity = velocity;
        
        IntStream.range(0, Global.NUM_STUDENTS).forEach((i) -> {
            int x = Global.uniform(0, Global.FLOOR_LENGTH - 1);
            int y = Global.uniform(0, Global.FLOOR_WIDTH - 1);
            Student student = new Student(x, y, this.meetingDuration, this.velocity, i);
            this.students.add(student);
        });
    }

    public void start() {
        this.students.forEach(s -> SignalList.SendSignal(Global.MOVE, this, Global.time, s));
    }

    public void TreatSignal(Signal x) {
        switch (x.signalType) {
            case Global.MOVE -> this.move((Student) x.signalData);
            case Global.MEET -> this.meet((Student) x.signalData);
            case Global.END_MEET -> this.endMeeting((Student) x.signalData);
        }
    }


    private void move(Student student) {
        Coordinates c = student.move();
        if (c == null) {
            return;
        }
//        System.out.println("Student " + student.getId() + " moved to (" + c.x + ", " + c.y + ")");

        int studentsOnTile = (int) this.students.stream().filter(s -> s.getX() == c.x && s.getY() == c.y).count();
        if (studentsOnTile == 2) {
            SignalList.SendSignal(Global.MEET, this, Global.time, student);
            return;
        }
        SignalList.SendSignal(Global.MOVE, this, Global.time + 1d/student.getVelocity(), student);
    }

    private void meet(Student student) {
        Student buddy = this.students.stream().filter(s -> s.getX() == student.getX() && s.getY() == student.getY() && s.getId() != student.getId()).findFirst().orElse(null);
        Arrays.stream(new Student[] {student, buddy}).forEach(s -> {
            s.meet();
            SignalList.SendSignal(Global.END_MEET, this, Global.time + this.meetingDuration, s);
        });
    }

    private void endMeeting(Student student) {
        Student buddy = this.students.stream().filter(s -> s.getX() == student.getX() && s.getY() == student.getY() && s.getId() != student.getId()).findFirst().orElse(null);
        student.endMeeting(buddy);

        SignalList.SendSignal(Global.MOVE, this, Global.time, student);
    }

    public boolean isDone() {
        return this.students.stream().allMatch(Student::isReady);
    }
    
    public List<Student> getStudents() {
        return this.students;
    }
}
