package com.thephoenixzone.campusstudent.model;

public class Attendance {
    private String subject;
    private int lectures;
    private int presents;
    private double percentage;


    public Attendance(String subject, int lectures, int presents, double percentage) {
        this.subject = subject;
        this.lectures = lectures;
        this.presents = presents;
        this.percentage = percentage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLectures() {
        return lectures;
    }

    public void setLectures(int lectures) {
        this.lectures = lectures;
    }

    public int getPresents() {
        return presents;
    }

    public void setPresents(int presents) {
        this.presents = presents;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
